import json
import unittest
from unittest.mock import patch

try:
    from scripts.validate_standard import main
except ModuleNotFoundError:
    from validate_standard import main


class ValidateStandardTest(unittest.TestCase):
    def test_self_check_passes(self):
        completed = type("Completed", (), {"returncode": 0, "stdout": "PASS tests\n", "stderr": ""})()
        with patch("scripts.validate_standard.subprocess.run", return_value=completed):
            self.assertEqual(main(["--self-check"]), 0)

    def test_json_output_is_structured(self):
        completed = type("Completed", (), {"returncode": 0, "stdout": "", "stderr": ""})()
        with patch("scripts.validate_standard.subprocess.run", return_value=completed):
            with patch("builtins.print") as output:
                self.assertEqual(main(["--self-check", "--json"]), 0)
                self.assertIn('"result": "PASS"', output.call_args.args[0])


if __name__ == "__main__":
    unittest.main()
