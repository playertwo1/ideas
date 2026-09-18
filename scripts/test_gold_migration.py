import json
import subprocess
import sys
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
        text = (ROOT / "project-manifest.yml").read_text(encoding="utf-8")
        for required in ("standard: gold", "fixtures/gold-valid/project.json", "G01: NOT_RUN"):
            self.assertIn(required, text)

    def test_valid_fixture_is_parseable(self) -> None:
        value = json.loads((ROOT / "fixtures/gold-valid/project.json").read_text())
        self.assertEqual(value["projectId"], "PRJ-GOLD-IDEAS")

    def test_invalid_fixture_is_rejected(self) -> None:
        value = json.loads((ROOT / "fixtures/gold-invalid/project.json").read_text())
        ids = [item["id"] for item in value["decisions"]]
        self.assertNotEqual(len(ids), len(set(ids)))


if __name__ == "__main__":
    unittest.main()
