# M0 — Validation Evidence

Este arquivo registra a evidência objetiva produzida durante F00.03–F00.05. Não substitui a auditoria independente do Codex nem registra `G00 = PASS`.

## Escopo validado

Arquivos de contrato:

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

## Execução realizada

Comando reproduzível: `python3 -m pip install -r requirements-dev.txt && python3 scripts/validate_contract.py`. A execução usou `jsonschema` 4.23.0 e checagem semântica determinística.

### Schema syntax

- `project.schema.json`: PASS
- `manifest.schema.json`: PASS
- `ai-response.schema.json`: PASS

### Fixtures positivas de projeto

- LIGHT: schema PASS; semantic PASS
- STANDARD: schema PASS; semantic PASS
- DEEP: schema PASS; semantic PASS

### Contratos auxiliares

- manifest válido: PASS
- envelope de resposta IA válido: PASS

### Fixtures negativas

- ID duplicado: o JSON Schema estrutural aceita a forma do documento, mas a camada semântica rejeita por `VAL-001`.
- referência interna não resolvida: o JSON Schema estrutural aceita a forma do documento, mas a camada semântica rejeita por `VAL-002`.
- decisão `author=AI`/`authority=AI`/`state=LOCKED`: a camada semântica rejeita por `VAL-003`; LOCKED exige autoridade humana explícita.

Esse comportamento é intencional: integridade referencial entre coleções e unicidade por propriedade `id` são tratadas como regras determinísticas de domínio, não falsamente atribuídas ao JSON Schema.

## Resultado de F00.03

Critério do roadmap: exemplo válido passa; ID duplicado e referência ausente falham.

**Resultado:** SATISFEITO para o contrato de fundação.

## Resultado de F00.05

Existem fixtures LIGHT, STANDARD e DEEP. A fixture DEEP registra capacidades futuras como limitações conhecidas e não inventa dados para completar o caso.

**Resultado:** SATISFEITO para o contrato de fundação.

## Limites desta evidência

- não existe implementação Android ainda;
- não valida Room, UI, provider de IA ou export real;
- não é `AUDIT RESULT: PASS`;
- não autoriza F01;
- não altera G00, que permanece `NOT_RUN` até auditoria e registro explícito da Product Authority.
