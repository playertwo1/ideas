# IDEA — PHASE CURRENT

> Contrato operacional da fase ativa. Derivado do `ROADMAP.md` v0.6. Em conflito, o `ROADMAP.md` vence. O checklist detalhado permanece em `EXECUTION_PLAN.md`.

## Fase ativa

**F02 — Skeleton Android e ciclo de desenvolvimento**

**Subetapa atual:** F02.05 — implementada pelo Builder, aguardando auditoria independente.

**Objetivo:** criar um skeleton Android instalável, com navegação base e verificações reproduzíveis.

## Resultado esperado

Fluxo, estados e arquitetura documentados de forma verificável, com domínio independente de Android e contratos portáteis preservados.

## Trabalho autorizado agora

- criar projeto, wrapper, catálogo de versões e módulos mínimos;
- implementar somente a navegação base e a Home vazia;
- documentar comandos reproduzíveis de build, lint e teste;
- manter testes e logs sem segredos ou dados reais;
- preservar os contratos e decisões LOCKED de F00/F01;
- atualizar `PROJECT_STATE.md` e `EXECUTION_PLAN.md` quando o estado realmente mudar.

## Não autorizado

- iniciar F03 antes de `G02 = PASS` registrado pela Product Authority;
- implementar F11–F20 / Idea Factory enquanto `G10 != PASS`;
- tratar a baseline de contexto como implementação de F15;
- alterar D01–D09 sem decisão explícita da Product Authority;
- criar segundo target, backend, sincronização ou abstração multiplataforma prematura;
- executar ação externa/destrutiva não necessária à fase.

## Decisões e gates vigentes

- D01–D09: `LOCKED` desde 13/09/2026;
- G00: `PASS` registrado em 14/09/2026 após auditoria independente;
- G01: `PASS` registrado pela Product Authority em 18/09/2026;
- G10: `NOT_RUN`;
- Idea Factory/F11–F20: bloqueada.

`NOT_RUN != PASS`. O Builder não registra o próprio gate.

## Subetapas e critérios

1. **F02.01 — Projeto e módulos mínimos:** concluída; evidência em `F02_01_EVIDENCE.json`.
2. **F02.02 — Tema, navegação e Home:** concluída; Home vazia e instalável.
3. **F02.03 — Comandos:** concluída; comandos em `F02_COMMANDS.md`.
4. **F02.04 — Verificações:** concluída; testes e lint sem segredos.
5. **F02.05 — Erros e logs:** concluída; log sintético `home_opened`, sem dados de usuário.

## Gate G01

**Estado:** `PASS`.

Critério: fluxo aprovado para implementação; modelo de custódia de credenciais e armazenamento resolvido, com provider específico homologado somente em F04.

## Retorno seguro

Se F01 ou G01 falhar, revisar wireframes, contratos e ADRs. Não há implementação nem dados de usuário a migrar nesta fase.

## Próxima ação

Auditar exclusivamente F02.01–F02.05. Não iniciar F03 nem registrar `G02 = PASS` antes da auditoria correspondente.
