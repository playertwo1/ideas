#!/usr/bin/env python3
"""Run the local Android F02 checks without CI services."""
from __future__ import annotations

import os
import subprocess
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
GRADLE = "gradlew.bat" if os.name == "nt" else "./gradlew"


def main() -> int:
    for task in ("assembleDebug", "test", "lint"):
        result = subprocess.run([GRADLE, "--no-daemon", task], cwd=ROOT)
        if result.returncode:
            return result.returncode
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
