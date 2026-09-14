# IDEA — PROJECT STATE

> Única fonte de status operacional dinâmico. Para regras canônicas use `ROADMAP.md`; para autorização da fase use `PHASE_CURRENT.md`; para evidência use o artefato de evidência correspondente.

- **Projeto:** Idea
- **Roadmap vigente:** v0.6
- **Release alvo:** MVP 0.1 / Idea Core
- **Milestone:** M0 — Contrato do produto
- **Fase:** F01 — Experiência e arquitetura da primeira versão
- **Subetapa atual:** F01.08 — concluída pelo Builder, aguardando auditoria independente
- **Última concluída:** F01.08 — contrato portátil e compatibilidade de schema
- **Próxima após a atual:** auditoria de F01/G01; F02 somente após `G01 = PASS` registrado pela Product Authority
- **G00:** PASS — auditoria independente e registro autorizado pela Product Authority em 14/09/2026
- **G10:** NOT_RUN
- **Factory desbloqueada:** NÃO
- **D01–D09:** LOCKED em 13/09/2026
- **Builder:** Antigravity
- **Auditor:** Codex
- **Product Authority:** usuário

## Estado atual

F00.01–F00.08 foram concluídas e verificadas. A auditoria independente da branch `docs/context-efficiency-f00`, incluindo a correção de paridade entre `AI_CONTEXT_INDEX.md` e `context-manifest.json`, resultou em `AUDIT RESULT: PASS`. A Product Authority autorizou e registrou `G00 = PASS` neste ciclo.

A baseline de eficiência de contexto foi materializada nesta branch em `M0_CONTEXT_EFFICIENCY.md`, `CONTEXT_POLICY.md`, `AI_CONTEXT_INDEX.md` e `context-manifest.json`. Isso não implementa F15 nem altera o gate atual.

## Evidência

- Evidência F00: `M0_VALIDATION_EVIDENCE.md`
- Pacote de auditoria: `G00_AUDIT_PACKET.md`
- Validador reproduzível: `scripts/validate_contract.py`
- Política de contexto: `CONTEXT_POLICY.md`

## Bloqueios

Nenhum bloqueio técnico conhecido para iniciar o trabalho documental de F01.

## Próxima ação

Submeter F01.08 à auditoria independente e preparar a avaliação de G01. Não registrar `G01 = PASS` nem iniciar F02/Android.

## Regra de avanço

F01 está autorizada. Android permanece não iniciado; implementação do skeleton pertence a F02 e depende de G01. `G10 = NOT_RUN` mantém a Idea Factory/F11–F20 bloqueada.
