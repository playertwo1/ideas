# IDEA — PROJECT STATE

> Estado operacional curto. `ROADMAP.md` v0.6 é o contrato canônico; `PHASE_CURRENT.md` autoriza o trabalho atual.

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

## Estado de F00

F00.01–F00.08 foram materializadas e verificadas no nível de contrato/documentação. Nenhuma D01–D09 foi reaberta.

Principais artefatos:

- `M0_PRODUCT_CONTRACT.md`
- `PROJECT_STANDARD.md`
- `schemas/`
- `VALIDATION_CONTRACT.md`
- `ARTIFACT_CATALOG.md`
- `fixtures/`
- `M0_DECISION_BASELINE.md`
- `M0_GOVERNANCE.md`
- `M0_VALIDATION_EVIDENCE.md`
- `G00_AUDIT_PACKET.md`

## Validação registrada

- três schemas em Draft 2020-12 verificados;
- fixtures LIGHT/STANDARD/DEEP passam na validação estrutural e semântica aplicável;
- caso de ID duplicado é rejeitado por `VAL-001`;
- caso de referência ausente é rejeitado por `VAL-002`;
- manifest e envelope de resposta IA válidos passam;
- `NOT_RUN` permanece distinto de `PASS`.

A evidência detalhada está em `M0_VALIDATION_EVIDENCE.md` e não substitui auditoria independente.

## Bloqueios

Nenhum bloqueio de fundação conhecido antes da auditoria. O bloqueio de avanço é procedimental e intencional: Codex ainda não emitiu o resultado de auditoria e a Product Authority ainda não registrou G00.

## Próxima ação

Codex/Auditor deve executar `G00_AUDIT_PACKET.md` conforme `AUDIT.md` e emitir `AUDIT RESULT: PASS|FAIL`.

Se houver finding corrigível dentro de F00, retornar ao Builder sem reabrir decisões humanas. Se houver nova decisão material, risco novo ou conflito canônico real, escalar à Product Authority.

## Gates

Builder produz evidências → Auditor emite `AUDIT RESULT: PASS|FAIL` → Product Authority registra o gate de desenvolvimento. Sem registro explícito, `NOT_RUN != PASS` e a fase não muda.

## Regra de atualização

Não iniciar F01 nem Android antes de G00 PASS explícito. Ao concluir a auditoria, registrar findings/resultado e então aguardar decisão da Product Authority sobre o gate.