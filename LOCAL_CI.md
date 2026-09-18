# CI local

O projeto possui uma verificação local reproduzível:

```powershell
python scripts/ci_local.py --json
```

Ela executa `python scripts/check.py` e `git diff --check`. GitHub Actions não
é requisito para o ciclo atual; a execução deve ocorrer localmente até haver
limite e autorização para CI remoto.
