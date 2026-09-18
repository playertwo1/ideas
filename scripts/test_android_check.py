import unittest
from pathlib import Path


class AndroidCheckTest(unittest.TestCase):
    def test_script_exists_and_targets_gradle_wrapper(self):
        text = Path(__file__).with_name("android_check.py").read_text(encoding="utf-8")
        self.assertIn("assembleDebug", text)
        self.assertIn("--no-daemon", text)


if __name__ == "__main__":
    unittest.main()
