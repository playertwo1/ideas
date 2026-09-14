#!/usr/bin/env python3
"""Adversarial regression checks for the F00 validation pipeline."""

from __future__ import annotations

import copy
import unittest

from validate_contract import (
    expect_value,
    load,
    semantic,
    structural,
    validate,
    validate_value,
)


class ValidationContractAdversarialTests(unittest.TestCase):
    def test_gate_duplicate_is_rejected(self) -> None:
        project = load("fixtures/light-simple.project.json")
        project["gates"].append(copy.deepcopy(project["gates"][0]))
        result = validate_value(project, "project")
        self.assertEqual(result["structural"], [])
        self.assertEqual(sorted(set(code for code, _ in result["semantic"])), ["VAL-001"])

    def test_roadmap_dependency_must_exist_and_cannot_self_reference(self) -> None:
        project = load("fixtures/light-simple.project.json")
        project["roadmap"][0]["dependsOn"] = ["PHASE-404"]
        result = validate_value(project, "project")
        self.assertEqual(result["structural"], [])
        self.assertEqual(sorted(set(code for code, _ in result["semantic"])), ["VAL-002"])

        project["roadmap"][0]["dependsOn"] = [project["roadmap"][0]["id"]]
        result = validate_value(project, "project")
        self.assertEqual(result["structural"], [])
        self.assertEqual(sorted(set(code for code, _ in result["semantic"])), ["VAL-002"])

    def test_not_applicable_requires_non_empty_rationale(self) -> None:
        project = load("fixtures/light-simple.project.json")
        project["gates"][0] = {"id": "GATE-CORE", "status": "NOT_APPLICABLE", "rationale": ""}
        result = validate_value(project, "project")
        self.assertEqual(result["structural"], [])
        self.assertEqual(sorted(set(code for code, _ in result["semantic"])), ["VAL-005"])

        project = load("fixtures/light-simple.project.json")
        project["roadmap"][0] = {
            "id": "PHASE-001",
            "text": "Item não aplicável.",
            "verify": "Registrar a não aplicação.",
            "gateStatus": "NOT_APPLICABLE",
            "rationale": "",
        }
        result = validate_value(project, "project")
        self.assertEqual(result["structural"], [])
        self.assertEqual(sorted(set(code for code, _ in result["semantic"])), ["VAL-005"])

        manifest = load("fixtures/valid.manifest.json")
        manifest["gateResults"] = [{"id": "GATE-CORE", "status": "NOT_APPLICABLE", "rationale": ""}]
        result = validate_value(manifest, "manifest")
        self.assertEqual(result["structural"], [])
        self.assertEqual(sorted(set(code for code, _ in result["semantic"])), ["VAL-005"])

        without_rationale = copy.deepcopy(project)
        del without_rationale["roadmap"][0]["rationale"]
        self.assertTrue(structural("project", without_rationale))

    def test_ai_or_non_human_locked_decision_is_rejected(self) -> None:
        result = validate("fixtures/invalid-ai-locked.project.json", "project")
        self.assertEqual(result["parse"], [])
        self.assertEqual(result["structural"], [])
        self.assertEqual(sorted(set(code for code, _ in result["semantic"])), ["VAL-003"])

    def test_namespace_rejects_n_a_and_uses_distinct_code(self) -> None:
        project = load("fixtures/light-simple.project.json")
        project["gates"][0]["status"] = "N_A"
        self.assertTrue(structural("project", project))

        project["gates"][0]["status"] = "NOT_RUN"
        project["references"] = [{"id": "REF-001", "label": "Fonte", "kind": "SOURCE"}]
        project["gates"][0]["id"] = "REF-001"
        semantic_codes = sorted(set(code for code, _ in semantic(project)))
        self.assertEqual(semantic_codes, ["VAL-001", "VAL-004"])

    def test_unexpected_error_does_not_false_pass(self) -> None:
        project = load("fixtures/invalid-duplicate-id.project.json")
        project["requirements"][1]["id"] = "REQ-002"
        with self.assertRaises(SystemExit) as context:
            expect_value(project, ["VAL-001"], "mutated negative fixture")
        self.assertNotEqual(context.exception.code, 0)

        project["requirements"][1]["id"] = "REQ-001"
        project["requirements"][1]["decisionRefs"] = ["DEC-404"]
        with self.assertRaises(SystemExit) as context:
            expect_value(project, ["VAL-001"], "negative fixture with unexpected VAL-002")
        self.assertNotEqual(context.exception.code, 0)

        project = load("fixtures/invalid-duplicate-gate-id.project.json")
        project["gates"].append(copy.deepcopy(project["gates"][0]))
        with self.assertRaises(SystemExit) as context:
            expect_value(project, ["VAL-001"], "negative fixture with extra VAL-001")
        self.assertNotEqual(context.exception.code, 0)


if __name__ == "__main__":
    unittest.main(verbosity=2)
