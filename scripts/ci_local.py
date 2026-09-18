#!/usr/bin/env python3
"""Minimal local CI contract; intentionally does not use GitHub Actions."""
from __future__ import annotations

import argparse
import json
import subprocess
import sys
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]


def commands() -> tuple[str, str]:
    return ("python scripts/check.py", "git diff --check")


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description="Run local project validation")
    parser.add_argument("--json", action="store_true", dest="as_json")
    args = parser.parse_args(argv)
    results = []
    check = subprocess.run([sys.executable, "scripts/check.py"], cwd=ROOT, capture_output=True, text=True)
    results.append({"command": commands()[0], "exit_code": check.returncode})
    if check.returncode == 0:
        diff = subprocess.run(["git", "diff", "--check"], cwd=ROOT, capture_output=True, text=True)
        results.append({"command": commands()[1], "exit_code": diff.returncode})
    result = "PASS" if all(item["exit_code"] == 0 for item in results) else "FAIL"
    report = {"result": result, "checks": results}
    print(json.dumps(report, indent=2) if args.as_json else f"{result}: local CI")
    return 0 if result == "PASS" else 1


if __name__ == "__main__":
    raise SystemExit(main())
