# IDEA — PHASE CURRENT

> Contrato operacional da fase ativa. Derivado do `ROADMAP.md` v0.6. Em conflito, o `ROADMAP.md` vence. O checklist detalhado permanece em `EXECUTION_PLAN.md`.

## Fase ativa

**F00 — Padrão de projeto e congelamento do MVP**

**Subestado:** F00.01–F00.08 materializadas; findings de preparação corrigidos; G00 aguarda nova auditoria independente e registro da Product Authority.

**Objetivo:** fechar contrato, governança, schemas, fixtures, validação e fronteiras do MVP antes de iniciar Android.

## Resultado esperado

Contrato versionado que permita identificar objetivamente o que falta em um pacote e impeça avanço para implementação sem os gates corretos.

## Trabalho autorizado agora

- corrigir finding objetivo de F00;
- refinar documentação, schemas, fixtures e validação de F00 sem alterar decisões `LOCKED`;
- reexecutar validações afetadas e atualizar evidências;
- preparar material para G00;
- reduzir duplicação/contexto operacional sem alterar semântica canônica;
- sincronizar `PROJECT_STATE.md`/`EXECUTION_PLAN.md` quando o estado realmente mudar.

## Não autorizado

- iniciar Android, Compose, Room ou provider real;
- avançar para F01 antes de G00 = PASS;
- implementar F11–F20 / Idea Factory;
- alterar materialmente MVP 0.1 sem Product Authority;
- reabrir D01–D09 por preferência técnica;
- criar segundo target ou abstração multiplataforma prematura;
- executar ação externa/destrutiva não necessária à F00.

## Decisões vigentes

- D01–D09: **LOCKED** desde 13/09/2026.
- Builder não promove sugestão/hipótese para decisão humana.
- Mudança material exige Product Authority e análise de impacto.
- F11–F20 permanecem bloqueadas até `G10 = PASS`.

Detalhes e redação canônica: `M0_DECISION_BASELINE.md`, `M0_GOVERNANCE.md` e seções correspondentes do `ROADMAP.md`.

## Contexto operacional

Use `AGENTS.md` + `PROJECT_STATE.md` como bootstrap. Para arquivos adicionais, use `AI_CONTEXT_INDEX.md` e `context-manifest.json`.

`WATCHDOG.md` e `AUDIT.md` entram por gatilho/tarefa conforme `CONTEXT_POLICY.md`; o ROADMAP inteiro não é leitura operacional padrão.

## Gate G00

**Estado:** `NOT_RUN`.

Critério canônico: três fixtures auditáveis, contrato versionado e bloqueadores de fundação resolvidos.

Pendências de saída:
- Codex executar auditoria proporcional independente;
- emitir `AUDIT RESULT: PASS` ou `FAIL`;
- se PASS e critérios objetivos estiverem satisfeitos, Product Authority registrar explicitamente `G00 = PASS`.

O Builder não registra o próprio gate.

## Retorno seguro

Se G00 falhar, permanecer em F00 e corrigir somente contratos/fixtures/regras afetados. Não avançar por inércia.

## Próxima ação

Executar `G00_AUDIT_PACKET.md` com contexto progressivo, corrigir findings bloqueantes se houver e manter F01 bloqueada até registro explícito de G00.
