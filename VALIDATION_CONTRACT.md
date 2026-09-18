# VALIDATION CONTRACT — Idea Core

Contrato determinístico dos formatos estruturados do Idea. Os schemas vivem em `schemas/`; as regras semânticas que não cabem em JSON Schema são verificadas por `scripts/validate_contract.py`.

## Pipeline

Sempre nesta ordem:

1. parse JSON;
2. validar o schema Draft 2020-12;
3. executar regras semânticas;
4. para fixtures negativas, comparar exatamente os códigos esperados.

Falha em qualquer etapa é `FAIL`. Ausência de execução é `NOT_RUN`.

## Regras semânticas

### VAL-001 — IDs materiais únicos

IDs cobertos pelo snapshot não podem se repetir, inclusive entre coleções incompatíveis:

- `DEC-*`;
- `REQ-*` / `NFR-*`;
- `PHASE-*` / `TASK-*`;
- `GAP-*`;
- `GATE-*`;
- `REF-*`.

### VAL-002 — referências internas existentes

Referências internas devem apontar para entidades existentes.

Cobertura atual:

- `requirements[].decisionRefs[]` → decisão existente;
- `roadmap[].dependsOn[]` → item existente;
- item de roadmap não depende de si mesmo.

### VAL-003 — LOCKED exige autoridade humana

Uma decisão `LOCKED` só é válida com:

- `author=USER`;
- `authority=USER`;
- `lockAction=HUMAN_EXPLICIT`.

IA, importação ou regra de sistema não podem fechar a decisão em nome do usuário.

### VAL-004 — namespace compatível

O prefixo do ID deve corresponder ao tipo de entidade definido pelo schema/contrato.

### VAL-005 — NOT_APPLICABLE exige rationale

`NOT_APPLICABLE` exige justificativa textual não vazia nos campos aplicáveis.

`NOT_RUN` continua diferente de `PASS`.

## Integridade

O validador nunca corrige silenciosamente um snapshot inválido. Não cria entidade ausente, remove referência problemática nem converte erro em warning para fazer o pacote passar.

## Fixtures

Fixtures válidas demonstram exemplos LIGHT, STANDARD e DEEP do contrato 0.1.

Fixtures negativas cobrem pelo menos:

- ID duplicado → `VAL-001`;
- referência ausente → `VAL-002`;
- IA tentando criar `LOCKED` → `VAL-003`;
- gate/roadmap duplicado → `VAL-001`;
- dependência de roadmap ausente → `VAL-002`;
- `NOT_APPLICABLE` sem rationale → `VAL-005`.

Os testes devem falhar se o defeito esperado desaparecer ou se surgir código semântico adicional inesperado.

## Contrato portátil

`project.json` e `manifest.json` são independentes de UI, Room e provider.

Leitura futura do pacote deve seguir:

```text
bytes
→ JSON parse
→ schema
→ regras semânticas
→ coerência manifest/projeto
→ integridade dos arquivos
→ snapshot candidato
```

Versão desconhecida/incompatível deve ser rejeitada explicitamente, nunca interpretada parcialmente.

## Verificação

Da raiz:

```bash
python scripts/check.py
```

Esse comando valida os contratos e executa os testes existentes. Android terá checks próprios quando B1 criar o aplicativo.
