"""The documented repository check runs the existing contract tests."""

import subprocess
import sys
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]


class RepositoryCheckTest(unittest.TestCase):
    def test_check_runs_contract_validation_and_tests(self):
        result = subprocess.run(
            [sys.executable, "scripts/check.py"],
            cwd=ROOT,
            capture_output=True,
            text=True,
        )
        self.assertEqual(0, result.returncode, result.stdout + result.stderr)
        self.assertIn("PASS contracts", result.stdout)
        self.assertIn("PASS tests", result.stdout)


if __name__ == "__main__":
    unittest.main()
