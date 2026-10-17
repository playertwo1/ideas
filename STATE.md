# STATE — ponto de retomada

Qualquer agente deve conseguir retomar só com este arquivo + a seção ativa do `ROADMAP.md`, sem a conversa anterior. Curto: o que não for estado atual sai daqui. Autorização da fase ativa: `PHASE_CURRENT.md`. Estado anterior completo: `docs/archive/PROJECT_STATE.md`.

**Atualizado:** 2026-10-17 · **Marco ativo:** M2 — MVP 0.1 / Idea Core (fase F09) · **Estado:** REVIEW

## O que funciona hoje (confirmado no código)
- App Android (`app/`) com persistência Room (`IdeaDatabase`, schema em `app/schemas/`) e recuperação local (F02–F03).
- Porta de provider de IA determinística com retry e orçamento de custo (F04); intake offline (F05); entrevista adaptativa e histórico de decisões (F06).
- Núcleo de planejamento e rastreabilidade (F07); roadmap e readiness com validação de IDs e dependências (F08).
- Renderer Markdown determinístico (F09.01).
- Validação local: `python scripts/check.py` (contratos + testes em `scripts/`).

## Última atividade
- ID / commit: F09.01 — `1ffe9f0` (2026-09-22), merge da correção de determinismo e validação do renderer.
- Resultado real: F09.01 implementada; aguarda auditoria independente.
- Testes executados: `python scripts/check.py` → PASS (contracts + tests) em 2026-10-17, após a reorganização de documentos; Gradle (`python scripts/android_check.py`) NOT_RUN.
- Evidência: `docs/fases/F09_EVIDENCE.json`.

## Gates
G00–G08 `PASS` (registrados pela Product Authority após auditoria independente) · G09 `NOT_RUN` · G10 `NOT_RUN` · Factory bloqueada · D01–D09 `LOCKED` (13/09/2026).

## Próxima ação
- F09.01 — auditoria independente (Auditor: Codex); começar por `docs/fases/F09_EVIDENCE.json` e o diff de `c6d6f9b..1ffe9f0`; não iniciar F09.02+ nem registrar `G09` sem PASS e autorização da Product Authority.

## Bloqueios e aprovações humanas
- `G09` e `G10` exigem registro explícito da Product Authority.
- F04.02–F04.07 constavam como "implementadas, aguardando reauditoria" em `docs/archive/PROJECT_STATE.md`, embora `G04 = PASS` tenha sido autorizado.

## Divergências entre roadmap e implementação
- Os registros de evidência `docs/fases/F0*_EVIDENCE.json` citam caminhos antigos na raiz (`PROJECT_STATE.md`, `F03_BACKUP_POLICY.md` etc.); foram preservados sem edição por serem evidência histórica.
- `G00_AUDIT_PACKET.md`, `M0_CONTEXT_EFFICIENCY.md` e `docs/GOLDEN_DIFF.md` ainda citam `PROJECT_STATE.md` (histórico, hoje em `docs/archive/`).
