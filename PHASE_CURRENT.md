# IDEA — PHASE CURRENT

> Contrato operacional da fase ativa. Derivado do `ROADMAP.md` v0.6. Em conflito, o `ROADMAP.md` vence. O checklist detalhado permanece em `EXECUTION_PLAN.md`.

## Fase ativa

**F03 — Persistência local e histórico confiável**

**Subetapa atual:** F03.06 — implementada pelo Builder, aguardando auditoria independente.

**Objetivo:** preservar projetos, revisões, rascunhos e eventos após reinício e falhas.

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

- iniciar F04 antes de `G03 = PASS` registrado pela Product Authority;
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
- G10: `NOT_RUN`;
- Idea Factory/F11–F20: bloqueada.

`NOT_RUN != PASS`. O Builder não registra o próprio gate.

## Subetapas e critérios

1. **F03.01:** entidades Room e DAOs com `projectId`.
2. **F03.02:** gravação de projeto e evento na mesma transação.
3. **F03.03:** entidade de rascunho e autosave local.
4. **F03.04:** schema Room exportado em `app/schemas/`.
5. **F03.05:** arquivar, restaurar e excluir por ID exato.
6. **F03.06:** política de backup e falhas sem fallback destrutivo.

## Gate G01

**Estado:** `PASS`.

Critério: fluxo aprovado para implementação; modelo de custódia de credenciais e armazenamento resolvido, com provider específico homologado somente em F04.

## Retorno seguro

Se F01 ou G01 falhar, revisar wireframes, contratos e ADRs. Não há implementação nem dados de usuário a migrar nesta fase.

## Próxima ação

Auditar exclusivamente F03.01–F03.06. Não iniciar F04 nem registrar `G03 = PASS` antes da auditoria correspondente.
