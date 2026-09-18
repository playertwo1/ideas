#!/usr/bin/env python3
"""Canonical local validation entry point for the Standard."""

from __future__ import annotations

import argparse
import json
import subprocess
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description="Validate the ideas Standard")
    parser.add_argument("--self-check", action="store_true", help="run all repository checks")
    parser.add_argument("--json", action="store_true", dest="as_json")
    args = parser.parse_args(argv)

    command = [sys.executable, "scripts/check.py"]
    result = subprocess.run(command, cwd=ROOT, capture_output=True, text=True)
    payload = {
        "result": "PASS" if result.returncode == 0 else "FAIL",
        "command": "python scripts/check.py",
        "exit_code": result.returncode,
    }
    if args.as_json:
        print(json.dumps(payload, indent=2))
    else:
        if result.stdout:
            print(result.stdout, end="")
        if result.stderr:
            print(result.stderr, end="", file=sys.stderr)
        print(f"{payload['result']}: standard validation")
    return result.returncode


if __name__ == "__main__":
    raise SystemExit(main())
