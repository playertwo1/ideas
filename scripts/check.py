#!/usr/bin/env python3
"""Run the repository's contract validation and unit tests."""

from __future__ import annotations

import subprocess
import sys
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]


def validate_target_file(path: Path) -> int | None:
    """Validate a project fixture directly; return None for non-project files."""
    if not path.is_file() or not path.name.endswith(".project.json"):
        return None
    try:
        from scripts.validate_contract import parse, semantic, structural
    except ModuleNotFoundError:
        from validate_contract import parse, semantic, structural

    value, parse_errors = parse(path)
    if parse_errors or not isinstance(value, dict):
        print(f"FAIL target parse: {path}")
        return 1
    errors = structural("project", value) + [message for _, message in semantic(value)]
    if errors:
        print(f"FAIL target validation: {path}")
        for error in errors:
            print(f"- {error}")
        return 1
    print(f"PASS target validation: {path}")
    return 0


def main() -> int:
    target = "."
    if len(sys.argv) > 1:
        target = sys.argv[1]
        if target in {"--json", "--details"}:
            target = "."
        if target not in {".", "--json", "--details"}:
            candidate = ROOT / target
            if not candidate.exists():
                print(f"FAIL target missing: {target}")
                return 2
            direct_result = validate_target_file(candidate)
            if direct_result is not None:
                return direct_result
    checks = (
        ("contracts", [sys.executable, "scripts/validate_contract.py"]),
        ("tests", [sys.executable, "-m", "unittest", "discover", "-s", "scripts", "-q"]),
    )
    for name, command in checks:
        result = subprocess.run(command, cwd=ROOT, capture_output=True, text=True)
        if result.returncode:
            print(f"FAIL {name} (exit {result.returncode})")
            if result.stdout:
                print(result.stdout, end="", file=sys.stderr)
            if result.stderr:
                print(result.stderr, end="", file=sys.stderr)
            return result.returncode
        print(f"PASS {name}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
