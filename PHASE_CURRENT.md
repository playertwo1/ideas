# IDEA — PHASE CURRENT

> Contrato operacional da fase ativa. Derivado do `ROADMAP.md` v0.6. Em conflito, o `ROADMAP.md` vence. O checklist detalhado permanece em `EXECUTION_PLAN.md`.

## Fase ativa

**F04 — Contrato de IA e execução controlada de geração**

**Subetapa atual:** F04.02–F04.07 — findings corrigidos, aguardando reauditoria independente.

**Objetivo:** executar sugestões estruturadas por uma porta provider-neutral, sem acoplar o domínio a rede, credenciais ou fornecedor.

## Resultado esperado

Fluxo, estados e arquitetura documentados de forma verificável, com domínio independente de Android e contratos portáteis preservados.

## Trabalho autorizado agora

- reauditar F04.02–F04.07;
- corrigir somente findings reproduzíveis de F04.02–F04.07;
- manter o fake offline, determinístico e sem credenciais;
- preservar os contratos e decisões LOCKED de F00–F03;
- atualizar `PROJECT_STATE.md` e `EXECUTION_PLAN.md` quando o estado realmente mudar.

## Não autorizado

- iniciar F05 antes da auditoria independente de F04.02–F04.07;
- implementar F11–F20 / Idea Factory enquanto `G10 != PASS`;
- tratar a baseline de contexto como implementação de F15;
- alterar D01–D09 sem decisão explícita da Product Authority;
- criar segundo target, backend, sincronização ou abstração multiplataforma prematura;
- executar ação externa/destrutiva não necessária à fase.

## Decisões e gates vigentes

- D01–D09: `LOCKED` desde 13/09/2026;
- G00: `PASS` registrado em 14/09/2026 após auditoria independente;
- G01: `PASS` registrado pela Product Authority em 18/09/2026;
- G02: `PASS` registrado após auditoria independente de F02;
- G03: `PASS` registrado pela Product Authority após auditoria independente de F03;
- G04: `NOT_RUN`;
- G10: `NOT_RUN`;
- Idea Factory/F11–F20: bloqueada.

`NOT_RUN != PASS`. O Builder não registra o próprio gate.

## Subetapas e critérios

1. **F04.01:** porta `AiProvider` e fake determinístico — PASS independente.
2. **F04.02–F04.07:** findings corrigidos, aguardando reauditoria.

## Gate G03

**Estado:** `PASS`.

Critério: F03 auditada independentemente; F04 autorizada pela Product Authority.

## Retorno seguro

Se F01 ou G01 falhar, revisar wireframes, contratos e ADRs. Não há implementação nem dados de usuário a migrar nesta fase.

## Próxima ação

Reauditar exclusivamente F04.02–F04.07. Não iniciar F05 nem registrar `G04 = PASS` antes da auditoria correspondente.
