# IDEA — PROJECT STATE

> Única fonte de status operacional dinâmico. Para regras canônicas use `ROADMAP.md`; para autorização da fase use `PHASE_CURRENT.md`; para evidência use o artefato de evidência correspondente.

- **Projeto:** Idea
- **Roadmap vigente:** v0.6
- **Release alvo:** MVP 0.1 / Idea Core
- **Milestone:** M1 — Fundação offline
- **Fase:** F02 — Skeleton Android e ciclo de desenvolvimento
- **Subetapa atual:** F02.05 — implementada pelo Builder, aguardando auditoria independente
- **Última concluída:** F01.08 — contrato portátil e compatibilidade de schema
- **Próxima após a atual:** auditar F02.01–F02.05; F03 somente após `G02 = PASS`
- **G00:** PASS — auditoria independente e registro autorizado pela Product Authority em 14/09/2026
- **G01:** PASS — registrado pela Product Authority após auditoria independente em 18/09/2026
- **G10:** NOT_RUN
- **Factory desbloqueada:** NÃO
- **D01–D09:** LOCKED em 13/09/2026
- **Builder:** Antigravity
- **Auditor:** Codex
- **Product Authority:** usuário

## Trilho Gold

- **Gold migration:** em execução documental, sem mudança de gate.
- **Baseline:** `docs/GOLD_MIGRATION_BASELINE.md`.
- **Golden Diff:** `docs/GOLDEN_DIFF.md`.
- **Validação:** `scripts/check.py`.
- **Runner:** externo e inalterado.

## Estado atual

F00.01–F00.08 foram concluídas e verificadas. A auditoria independente da branch `docs/context-efficiency-f00`, incluindo a correção de paridade entre `AI_CONTEXT_INDEX.md` e `context-manifest.json`, resultou em `AUDIT RESULT: PASS`. A Product Authority autorizou e registrou `G00 = PASS` neste ciclo.

A baseline de eficiência de contexto foi materializada nesta branch em `M0_CONTEXT_EFFICIENCY.md`, `CONTEXT_POLICY.md`, `AI_CONTEXT_INDEX.md` e `context-manifest.json`. Isso não implementa F15 nem altera o gate atual.

## Evidência

- Evidência F00: `M0_VALIDATION_EVIDENCE.md`
- Pacote de auditoria: `G00_AUDIT_PACKET.md`
- Validador reproduzível: `scripts/validate_contract.py`
- Política de contexto: `CONTEXT_POLICY.md`

## Bloqueios

F02.01–F02.05 foram implementadas. A auditoria independente de F02 está pendente.

## Próxima ação

Auditar exclusivamente F02.01–F02.05. Não iniciar F03 nem registrar `G02 = PASS` antes da auditoria correspondente.

## Regra de avanço

F02 está autorizada após `G01 = PASS`; Android ainda não foi implementado. `G10 = NOT_RUN` mantém a Idea Factory/F11–F20 bloqueada.
