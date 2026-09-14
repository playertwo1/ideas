#!/usr/bin/env python3
import hashlib
import json
import unittest
from pathlib import Path

from jsonschema import Draft202012Validator


ROOT = Path(__file__).resolve().parents[1]
PROJECT_PATH = ROOT / "fixtures" / "standard-medium.project.json"
MANIFEST_PATH = ROOT / "fixtures" / "valid.manifest.json"


class PortableContractTests(unittest.TestCase):
    def test_fixture_pair_is_platform_independent_and_coherent(self) -> None:
        project_bytes = PROJECT_PATH.read_bytes()
        project = json.loads(project_bytes)
        manifest = json.loads(MANIFEST_PATH.read_text(encoding="utf-8"))

        project_schema = json.loads(
            (ROOT / "schemas" / "project.schema.json").read_text(encoding="utf-8")
        )
        manifest_schema = json.loads(
            (ROOT / "schemas" / "manifest.schema.json").read_text(encoding="utf-8")
        )
        Draft202012Validator(project_schema).validate(project)
        Draft202012Validator(manifest_schema).validate(manifest)

        self.assertEqual(project["projectId"], manifest["projectId"])
        self.assertEqual(project["schemaVersion"], manifest["schemaVersion"])
        project_entry = next(item for item in manifest["files"] if item["path"] == "project.json")
        self.assertEqual(hashlib.sha256(project_bytes).hexdigest(), project_entry["sha256"])


if __name__ == "__main__":
    unittest.main(verbosity=2)
