import json
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]


class GoldMigrationTest(unittest.TestCase):
    def run_check(self, path: str) -> subprocess.CompletedProcess[str]:
        return subprocess.run(
            [sys.executable, "scripts/check.py", path, "--json"],
            cwd=ROOT,
            capture_output=True,
            text=True,
        )

    def test_manifest_declares_gold_composition(self) -> None:
        value = json.loads((ROOT / "project-manifest.yml").read_text(encoding="utf-8"))
        self.assertEqual(value["standard"], "gold")
        self.assertEqual(value["fixtures"]["valid"], "fixtures/gold-valid/project.json")
        self.assertEqual(value["gates"]["G01"], "NOT_RUN")

    def test_valid_fixture_is_parseable(self) -> None:
        value = json.loads((ROOT / "fixtures/gold-valid/project.json").read_text())
        self.assertEqual(value["projectId"], "PRJ-GOLD-IDEAS")

    def test_invalid_fixture_is_rejected(self) -> None:
        value = json.loads((ROOT / "fixtures/gold-invalid/project.json").read_text())
        ids = [item["id"] for item in value["decisions"]]
        self.assertNotEqual(len(ids), len(set(ids)))

    def test_check_rejects_invalid_target_file(self) -> None:
        result = subprocess.run(
            [sys.executable, "scripts/check.py", "fixtures/invalid-duplicate-id.project.json"],
            cwd=ROOT,
            capture_output=True,
            text=True,
        )
        self.assertNotEqual(result.returncode, 0)

    def test_gold_manifest_schema_rejects_missing_field(self) -> None:
        from scripts.check import validate_gold_manifest

        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / "project-manifest.yml"
            path.write_text('{"version": 1, "project": "ideas"}', encoding="utf-8")
            valid, errors = validate_gold_manifest(path)
        self.assertFalse(valid)
        self.assertTrue(errors)


if __name__ == "__main__":
    unittest.main()
