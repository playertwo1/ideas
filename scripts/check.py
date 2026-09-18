#!/usr/bin/env python3
"""Run the repository's contract validation and unit tests."""

from __future__ import annotations

import subprocess
import sys
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]


def main() -> int:
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
