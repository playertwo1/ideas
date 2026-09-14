# IDEA — PROJECT STATE

> Única fonte de status operacional dinâmico. Para regras canônicas use `ROADMAP.md`; para autorização da fase use `PHASE_CURRENT.md`; para evidência use o artefato de evidência correspondente.

- **Projeto:** Idea
- **Roadmap vigente:** v0.6
- **Release alvo:** MVP 0.1 / Idea Core
- **Milestone:** M0 — Contrato do produto
- **Fase:** F00 — Padrão de projeto e congelamento do MVP
- **Subetapa atual:** G00 — auditoria de saída de F00
- **Última concluída:** F00.08
- **Próxima após a atual:** F01, somente se G00 = PASS for registrado pela Product Authority
- **G00:** NOT_RUN
- **G10:** NOT_RUN
- **Factory desbloqueada:** NÃO
- **D01–D09:** LOCKED em 13/09/2026
- **Builder:** Antigravity
- **Auditor:** Codex
- **Product Authority:** usuário

## Estado atual

F00.01–F00.08 foram materializadas e verificadas no nível de contrato/documentação. Findings anteriores de preparação foram corrigidos; a saída da F00 ainda depende de nova auditoria independente do Codex e do registro explícito de G00 pela Product Authority.

A baseline de eficiência de contexto foi materializada nesta branch em `M0_CONTEXT_EFFICIENCY.md`, `CONTEXT_POLICY.md`, `AI_CONTEXT_INDEX.md` e `context-manifest.json`. Isso não implementa F15 nem altera o gate atual.

## Evidência

- Evidência F00: `M0_VALIDATION_EVIDENCE.md`
- Pacote de auditoria: `G00_AUDIT_PACKET.md`
- Validador reproduzível: `scripts/validate_contract.py`
- Política de contexto: `CONTEXT_POLICY.md`

## Bloqueios

Nenhum bloqueio técnico de fundação conhecido antes da auditoria.

Bloqueio de avanço intencional: G00 ainda não recebeu `AUDIT RESULT: PASS` independente nem registro explícito da Product Authority.

## Próxima ação

Codex/Auditor deve executar `G00_AUDIT_PACKET.md` conforme `AUDIT.md`, usando contexto progressivo conforme `AI_CONTEXT_INDEX.md`, e emitir `AUDIT RESULT: PASS|FAIL`.

Finding corrigível dentro de F00 retorna ao Builder. Nova decisão material, risco novo ou conflito canônico real escala à Product Authority.

## Regra de avanço

Não iniciar F01 nem Android antes de G00 PASS explícito.

Builder produz evidências → Auditor emite PASS/FAIL → Product Authority registra o gate.
