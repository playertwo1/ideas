#!/usr/bin/env python3
"""Deterministic F00 parse, structural, semantic, and expectation checks."""

from __future__ import annotations

import json
from pathlib import Path
from typing import Any

from jsonschema import Draft202012Validator, FormatChecker

ROOT = Path(__file__).resolve().parents[1]
SCHEMAS = {
    path.name.replace(".schema.json", ""): json.loads(path.read_text(encoding="utf-8"))
    for path in (ROOT / "schemas").glob("*.json")
}

MATERIAL_COLLECTIONS = (
    ("decisions", "DEC-"),
    ("requirements", ("REQ-", "NFR-")),
    ("roadmap", ("PHASE-", "TASK-")),
    ("gaps", "GAP-"),
    ("gates", "GATE-"),
    ("references", "REF-"),
)


def load(path: str | Path) -> Any:
    return json.loads((ROOT / path).read_text(encoding="utf-8"))


def parse(path: str | Path) -> tuple[Any | None, list[str]]:
    """Parse one JSON document without conflating parse errors with later stages."""
    try:
        return load(path), []
    except (OSError, UnicodeDecodeError, json.JSONDecodeError) as error:
        return None, [f"{type(error).__name__}: {error}"]


def structural(schema_name: str, value: Any) -> list[str]:
    schema = SCHEMAS[schema_name]
    Draft202012Validator.check_schema(schema)
    errors = sorted(
        Draft202012Validator(schema, format_checker=FormatChecker()).iter_errors(value),
        key=lambda error: list(error.path),
    )
    return [
        f"{'.'.join(map(str, error.path)) or '$'}: {error.message}"
        for error in errors
    ]


def _has_prefix(value: str, prefixes: str | tuple[str, ...]) -> bool:
    choices = prefixes if isinstance(prefixes, tuple) else (prefixes,)
    return any(value.startswith(prefix) for prefix in choices)


def _semantic_error(code: str, message: str) -> tuple[str, str]:
    return code, f"{code} {message}"


def semantic(project: dict[str, Any]) -> list[tuple[str, str]]:
    """Apply deterministic domain rules to an already structural project."""
    errors: list[tuple[str, str]] = []
    seen: dict[str, str] = {}
    decision_ids: set[str] = set()
    roadmap_ids: set[str] = set()

    for collection, prefixes in MATERIAL_COLLECTIONS:
        for index, item in enumerate(project.get(collection, [])):
            item_id = item.get("id")
            location = f"{collection}[{index}].id"
            if not isinstance(item_id, str):
                continue
            if item_id in seen:
                errors.append(
                    _semantic_error(
                        "VAL-001",
                        f"duplicate id {item_id} ({seen[item_id]} and {location})",
                    )
                )
            else:
                seen[item_id] = location
            if not _has_prefix(item_id, prefixes):
                errors.append(
                    _semantic_error(
                        "VAL-004",
                        f"namespace incompatible for {location}: {item_id}",
                    )
                )
            if collection == "decisions":
                decision_ids.add(item_id)
            if collection == "roadmap":
                roadmap_ids.add(item_id)

    for index, decision in enumerate(project.get("decisions", [])):
        if decision.get("state") == "LOCKED" and (
            decision.get("author") != "USER"
            or decision.get("authority") != "USER"
            or decision.get("lockAction") != "HUMAN_EXPLICIT"
        ):
            errors.append(
                _semantic_error(
                    "VAL-003",
                    "LOCKED decision lacks explicit human authority "
                    f"decisions[{index}].id={decision.get('id')}",
                )
            )

    for index, requirement in enumerate(project.get("requirements", [])):
        for ref_index, reference in enumerate(requirement.get("decisionRefs", [])):
            if reference not in decision_ids:
                errors.append(
                    _semantic_error(
                        "VAL-002",
                        f"missing reference {reference} at requirements[{index}].decisionRefs[{ref_index}]",
                    )
                )

    for index, item in enumerate(project.get("roadmap", [])):
        item_id = item.get("id")
        if item.get("gateStatus") == "NOT_APPLICABLE":
            rationale = item.get("rationale")
            if not isinstance(rationale, str) or not rationale.strip():
                errors.append(
                    _semantic_error(
                        "VAL-005",
                        f"NOT_APPLICABLE roadmap item requires non-empty rationale at roadmap[{index}]",
                    )
                )
        for ref_index, dependency in enumerate(item.get("dependsOn", [])):
            if dependency not in roadmap_ids:
                errors.append(
                    _semantic_error(
                        "VAL-002",
                        f"missing roadmap dependency {dependency} at roadmap[{index}].dependsOn[{ref_index}]",
                    )
                )
            if dependency == item_id:
                errors.append(
                    _semantic_error(
                        "VAL-002",
                        f"roadmap item cannot depend on itself at roadmap[{index}].dependsOn[{ref_index}]",
                    )
                )

    for index, gate in enumerate(project.get("gates", [])):
        if gate.get("status") == "NOT_APPLICABLE":
            rationale = gate.get("rationale")
            if not isinstance(rationale, str) or not rationale.strip():
                errors.append(
                    _semantic_error(
                        "VAL-005",
                        f"NOT_APPLICABLE gate requires non-empty rationale at gates[{index}]",
                    )
                )

    return errors


def semantic_manifest(manifest: dict[str, Any]) -> list[tuple[str, str]]:
    errors: list[tuple[str, str]] = []
    for index, gate in enumerate(manifest.get("gateResults", [])):
        if gate.get("status") == "NOT_APPLICABLE":
            rationale = gate.get("rationale")
            if not isinstance(rationale, str) or not rationale.strip():
                errors.append(
                    _semantic_error(
                        "VAL-005",
                        f"NOT_APPLICABLE manifest gate requires non-empty rationale at gateResults[{index}]",
                    )
                )
    return errors


def validate(path: str | Path, schema_name: str) -> dict[str, Any]:
    value, parse_errors = parse(path)
    result: dict[str, Any] = {
        "value": value,
        "parse": parse_errors,
        "structural": [],
        "semantic": [],
    }
    if parse_errors:
        return result
    result["structural"] = structural(schema_name, value)
    if result["structural"]:
        return result
    if schema_name == "project":
        result["semantic"] = semantic(value)
    elif schema_name == "manifest":
        result["semantic"] = semantic_manifest(value)
    return result


def validate_value(value: Any, schema_name: str) -> dict[str, Any]:
    """Run the same structural/semantic pipeline for an in-memory adversarial value."""
    result: dict[str, Any] = {
        "value": value,
        "parse": [],
        "structural": structural(schema_name, value),
        "semantic": [],
    }
    if result["structural"]:
        return result
    if schema_name == "project":
        result["semantic"] = semantic(value)
    elif schema_name == "manifest":
        result["semantic"] = semantic_manifest(value)
    return result


def _codes(errors: list[tuple[str, str]]) -> list[str]:
    return [code for code, _ in errors]


def _fail(label: str, result: dict[str, Any], expected: list[str] | None = None) -> None:
    details: dict[str, Any] = {
        "parse": result["parse"],
        "structural": result["structural"],
        "semantic": [message for _, message in result["semantic"]],
    }
    if expected is not None:
        details["expectedSemanticCodes"] = expected
    raise SystemExit(f"FAIL {label}: {json.dumps(details, ensure_ascii=False)}")


def expect(path: str | Path, expected_codes: list[str]) -> None:
    result = validate(path, "project")
    expect_result(str(path), result, expected_codes)


def expect_result(label: str, result: dict[str, Any], expected_codes: list[str]) -> None:
    if result["parse"] or result["structural"]:
        _fail(label, result, expected_codes)
    actual_codes = sorted(_codes(result["semantic"]))
    expected = sorted(expected_codes)
    if actual_codes != expected:
        _fail(label, result, expected_codes)
    print(
        f"PASS negative fixture {label}: parse PASS; structural PASS; "
        f"semantic codes exactly {actual_codes}"
    )


def expect_value(value: Any, expected_codes: list[str], label: str = "in-memory value") -> None:
    """Assert exact semantic codes for tests that mutate a fixture adversarially."""
    expect_result(label, validate_value(value, "project"), expected_codes)


def main() -> None:
    for name in ("project", "manifest", "ai-response"):
        Draft202012Validator.check_schema(SCHEMAS[name])
        print(f"PASS schema syntax {name} (Draft 2020-12)")

    for path in (
        "fixtures/light-simple.project.json",
        "fixtures/standard-medium.project.json",
        "fixtures/deep.project.json",
    ):
        result = validate(path, "project")
        if result["parse"] or result["structural"] or result["semantic"]:
            _fail(f"valid project {path}", result)
        print(f"PASS valid fixture {path}: parse PASS; structural PASS; semantic PASS")

    for schema_name, path in (
        ("manifest", "fixtures/valid.manifest.json"),
        ("ai-response", "fixtures/valid.ai-response.json"),
    ):
        result = validate(path, schema_name)
        if result["parse"] or result["structural"] or result["semantic"]:
            _fail(f"valid {schema_name} {path}", result)
        print(f"PASS valid {path}: parse PASS; structural PASS; semantic PASS")

    expected_negative_fixtures = {
        "fixtures/invalid-duplicate-id.project.json": ["VAL-001"],
        "fixtures/invalid-missing-ref.project.json": ["VAL-002"],
        "fixtures/invalid-ai-locked.project.json": ["VAL-003"],
        "fixtures/invalid-duplicate-gate-id.project.json": ["VAL-001"],
        "fixtures/invalid-duplicate-roadmap-id.project.json": ["VAL-001"],
        "fixtures/invalid-missing-roadmap-ref.project.json": ["VAL-002"],
        "fixtures/invalid-not-applicable-rationale.project.json": ["VAL-005"],
    }
    for path, expected_codes in expected_negative_fixtures.items():
        expect(path, expected_codes)
    print("PASS semantic validation suite: exact stage and code assertions")


if __name__ == "__main__":
    main()
