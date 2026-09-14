# M0 — Validation Evidence

Esta evidência registra somente execuções realizadas em 13/09/2026. Não
substitui auditoria independente do Codex, não registra `G00 = PASS` e não
autoriza F01.

## Escopo validado

Contratos:

- `schemas/project.schema.json`
- `schemas/manifest.schema.json`
- `schemas/ai-response.schema.json`
- `VALIDATION_CONTRACT.md`

Fixtures positivas:

- `fixtures/light-simple.project.json`
- `fixtures/standard-medium.project.json`
- `fixtures/deep.project.json`
- `fixtures/valid.manifest.json`
- `fixtures/valid.ai-response.json`

Fixtures negativas:

- `fixtures/invalid-duplicate-id.project.json`
- `fixtures/invalid-missing-ref.project.json`
- `fixtures/invalid-ai-locked.project.json`
- `fixtures/invalid-duplicate-gate-id.project.json`
- `fixtures/invalid-duplicate-roadmap-id.project.json`
- `fixtures/invalid-missing-roadmap-ref.project.json`
- `fixtures/invalid-not-applicable-rationale.project.json`

## Pipeline executado

O script `scripts/validate_contract.py` executa, nesta ordem, parse JSON,
validação estrutural Draft 2020-12, validação semântica e comparação exata dos
códigos esperados. A camada semântica só roda após parse e estrutura passarem.

O comando solicitado `python3 -m pip install -r requirements-dev.txt` foi
tentado, mas o alias `python3` do Windows apontou para o instalador da Microsoft
Store e não executou Python. O mesmo procedimento foi executado com o runtime
disponível:

```text
python -m pip install -r requirements-dev.txt — exit 0
jsonschema 4.23.0 instalado
```

## Resultado do validador

```text
python scripts/validate_contract.py — exit 0
PASS schema syntax project (Draft 2020-12)
PASS schema syntax manifest (Draft 2020-12)
PASS schema syntax ai-response (Draft 2020-12)
PASS valid fixture LIGHT/STANDARD/DEEP: parse PASS; structural PASS; semantic PASS
PASS valid manifest e ai-response: parse PASS; structural PASS; semantic PASS
PASS negative fixtures: parse PASS; structural PASS; semantic codes exatos
  invalid-duplicate-id                 → VAL-001
  invalid-missing-ref                  → VAL-002
  invalid-ai-locked                    → VAL-003
  invalid-duplicate-gate-id             → VAL-001
  invalid-duplicate-roadmap-id          → VAL-001
  invalid-missing-roadmap-ref           → VAL-002
  invalid-not-applicable-rationale      → VAL-005
PASS semantic validation suite: exact stage and code assertions
```

## Resultado dos testes adversariais

```text
python scripts/test_validate_contract.py — exit 0
Ran 6 tests in 0.270s
OK
```

Os testes comprovaram:

- duplicidade de gate e de roadmap não passa silenciosamente;
- dependência inexistente e autorreferência falham por `VAL-002`;
- `NOT_APPLICABLE` com rationale vazio falha por `VAL-005`, e o
  campo ausente também é rejeitado estruturalmente pelo schema;
- `AI`/autoridade não humana + `LOCKED` falha por `VAL-003`;
- `N_A` não é aceito pelo schema;
- colisão entre coleções e namespace incompatível são distinguidos por
  `VAL-001` e `VAL-004`;
- remover o defeito esperado ou introduzir `VAL-002` adicional faz a
  comparação exata falhar com `SystemExit` não zero, sem falso PASS.

## Códigos semânticos finais

- `VAL-001`: ID material duplicado;
- `VAL-002`: referência interna inexistente ou autorreferência de roadmap;
- `VAL-003`: decisão `LOCKED` sem `author=USER`, `authority=USER` e
  `lockAction=HUMAN_EXPLICIT`;
- `VAL-004`: namespace incompatível;
- `VAL-005`: `NOT_APPLICABLE` sem rationale textual não vazio.

`NOT_RUN` permanece diferente de `PASS`. `NOT_APPLICABLE` é o valor executável
único; `N_A` não é aceito por schema.

## Limites e estado

- não existe implementação Android;
- F01 não foi iniciada;
- Idea Factory/F11–F20 não foram implementadas;
- D01–D09 permanecem `LOCKED`;
- `G00` permanece `NOT_RUN`;
- `G10` permanece `NOT_RUN`;
- esta evidência não é `AUDIT RESULT: PASS`.
