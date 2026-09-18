# Idea Gold Migration Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Migrar `ideas` para o trilho Gold Standard com validação e evidência reproduzíveis, preservando F01 e seus gates.

**Architecture:** A documentação canônica será separada por responsabilidade: `PROJECT_STATE.md` guarda status, `ROADMAP.md` guarda contrato de fases e `PHASE_CURRENT.md` guarda autorização. `scripts/check.py` será a entrada única; manifesto, fixtures, Golden Diff e evidência provarão a adoção sem copiar o Runner.

**Tech Stack:** Markdown, YAML/JSON, Python 3, unittest e os validadores existentes.

**Spec:** `docs/superpowers/specs/2026-09-18-ideas-gold-migration-design.md`

## Global Constraints

- Preservar F01.08, `G00 = PASS`, `G01 = NOT_RUN`, `G10 = NOT_RUN` e D01–D09 `LOCKED`.
- Não iniciar F02/Android, F11–F20 ou Runner.
- Não registrar gate ou aprovação automática.
- Não remover artefato sem registrar a decisão em `docs/GOLDEN_DIFF.md`.

---

### Task 1: Congelar baseline e Golden Diff

**Files:**
- Create: `docs/GOLDEN_DIFF.md`
- Create: `docs/GOLD_MIGRATION_BASELINE.md`

- [ ] **Step 1: Registrar o SHA atual, árvore limpa e fontes examinadas**
- [ ] **Step 2: Classificar cada artefato como ADOPT, KEEP, REJECT ou REPLACE**
- [ ] **Step 3: Registrar razões e impacto sem alterar estado de produto**
- [ ] **Step 4: Run: `git diff --check`**
- [ ] **Step 5: Commit: `docs: record Gold migration baseline and diff`**

### Task 2: Consolidar contrato documental

**Files:**
- Modify: `README.md`
- Modify: `ROADMAP.md`
- Modify: `PROJECT_STATE.md`
- Modify: `AGENTS.md`
- Modify: `PHASE_CURRENT.md`

- [ ] **Step 1: Remover status duplicado do README e apontar para `PROJECT_STATE.md`**
- [ ] **Step 2: Reescrever o ROADMAP para separar F01, gates e fases futuras**
- [ ] **Step 3: Atualizar PROJECT_STATE somente com o estado existente**
- [ ] **Step 4: Manter AGENTS mínimo, com autoridade, contexto e validação**
- [ ] **Step 5: Confirmar que PHASE_CURRENT continua bloqueando F02 antes de G01**
- [ ] **Step 6: Run: `python scripts/validate_contract.py`**
- [ ] **Step 7: Commit: `docs: align Idea documents with Gold contract`**

### Task 3: Manifesto e fixtures Gold

**Files:**
- Create: `project-manifest.yml`
- Create: `fixtures/gold-valid/project.json`
- Create: `fixtures/gold-invalid/project.json`
- Modify: `scripts/check.py`
- Modify: `scripts/validate_contract.py`
- Test: `scripts/test_gold_migration.py`

- [ ] **Step 1: Escrever teste para PASS da fixture válida e FAIL da inválida**
- [ ] **Step 2: Executar o teste e confirmar falha inicial**
- [ ] **Step 3: Implementar manifesto e validação sem duplicar o validador existente**
- [ ] **Step 4: Fazer `scripts/check.py` executar contrato, testes e manifesto**
- [ ] **Step 5: Executar `python -m unittest scripts.test_gold_migration`**
- [ ] **Step 6: Commit: `feat: add Gold manifest and fixtures`**

### Task 4: Evidência e dogfooding

**Files:**
- Create: `GOLD_MIGRATION_EVIDENCE.json`
- Create: `examples/gold-standard/project.json`
- Create: `examples/gold-standard/README.md`
- Create: `examples/gold-standard/manifest.json`
- Modify: `scripts/test_gold_migration.py`

- [ ] **Step 1: Gerar digests SHA-256 dos artefatos commitados**
- [ ] **Step 2: Validar o próprio `ideas` como fixture Gold**
- [ ] **Step 3: Validar `examples/gold-standard/` com `scripts/check.py`**
- [ ] **Step 4: Reexecutar dogfooding duas vezes e confirmar saída idempotente**
- [ ] **Step 5: Atualizar evidência somente após todas as verificações**
- [ ] **Step 6: Commit: `test: add Gold migration evidence and dogfooding`**

### Task 5: Verificação final e pacote para auditoria

**Files:**
- Modify: `GOLD_MIGRATION_EVIDENCE.json`

- [ ] **Step 1: Run: `python scripts/check.py`**
- [ ] **Step 2: Run: `python scripts/validate_contract.py`**
- [ ] **Step 3: Run: `python -m unittest discover -s scripts -q`**
- [ ] **Step 4: Run: `python scripts/check.py fixtures/gold-valid --json`**
- [ ] **Step 5: Run: `python scripts/check.py fixtures/gold-invalid --json` e confirmar código não zero**
- [ ] **Step 6: Run: `git diff --check`**
- [ ] **Step 7: Confirmar F01.08, G00, G01, G10, F02 e Runner no diff final**
- [ ] **Step 8: Commit: `chore: finalize Gold migration evidence`**
