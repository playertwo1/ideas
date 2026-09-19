# IDEA — PROJECT STATE

> Única fonte de status operacional dinâmico. Para regras canônicas use `ROADMAP.md`; para autorização da fase use `PHASE_CURRENT.md`; para evidência use o artefato de evidência correspondente.

- **Projeto:** Idea
- **Roadmap vigente:** v0.6
- **Release alvo:** MVP 0.1 / Idea Core
- **Milestone:** M2 — MVP 0.1 / Idea Core
- **Fase:** F09 — Exportação Markdown e restauração
- **Subetapa atual:** F09.01 — implementada, aguardando auditoria independente
- **Última concluída:** F08.01–F08.05 — auditoria independente PASS no SHA `dfecf412d58168370efeb5035985d1318e94f321`; `G08 = PASS` autorizado pela Product Authority
- **Próxima após a atual:** auditoria independente de F09.01; não iniciar F09.02+ nem registrar `G09`
- **G00:** PASS — auditoria independente e registro autorizado pela Product Authority em 14/09/2026
- **G01:** PASS — registrado pela Product Authority após auditoria independente em 18/09/2026
- **G02:** PASS — registrado após auditoria independente de F02
- **G03:** PASS — registrado pela Product Authority após auditoria independente de F03 em 18/09/2026
- **G04:** PASS — autorizado pela Product Authority em 18/09/2026; F05 liberada
- **G05:** PASS — autorizado pela Product Authority em 18/09/2026; F06 liberada
- **G06:** PASS — registrado pela Product Authority após reauditoria independente de F06
- **G07:** PASS — autorizado pela Product Authority após auditoria independente de F07
- **G08:** PASS — autorizado pela Product Authority após auditoria independente de F08
- **G09:** NOT_RUN
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
- Evidência F07: `F07_EVIDENCE.json`
- Pacote de auditoria: `G00_AUDIT_PACKET.md`
- Validador reproduzível: `scripts/validate_contract.py`
- Política de contexto: `CONTEXT_POLICY.md`

## Bloqueios

F03.01–F03.06 foram auditadas independentemente com PASS. F04.01 foi auditada com PASS; F04.02–F04.07 estão implementadas e aguardam reauditoria. F05.01–F05.05 foram auditadas independentemente com PASS. F06.01–F06.07 foram reauditadas independentemente com PASS no SHA `905660e77c2f0b05ac1ef37f60fa332a341add6d`. F07.01–F07.06 foram auditadas independentemente com PASS no SHA `0c7c3f201aec8ddd77d9418a199e5570ca8bb375`; F08.01–F08.05 foram auditadas com PASS; F09.01 está implementada e aguarda auditoria.

## Próxima ação

F08.01–F08.05 foram auditadas com PASS pelo Auditor (Codex) e `G08 = PASS` foi explicitamente autorizado pela Product Authority. F09.01 está implementada e aguarda auditoria; não iniciar F09.02+ nem registrar `G09`.

## Regra de avanço

F09.01 implementada aguardando auditoria; `G09 = NOT_RUN`; `G10 = NOT_RUN` mantém a Idea Factory/F11–F20 bloqueada.
