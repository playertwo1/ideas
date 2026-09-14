# IDEA — PHASE CURRENT

> Contrato operacional da fase ativa. Derivado do `ROADMAP.md` v0.6. Em conflito, o `ROADMAP.md` vence. O checklist detalhado permanece em `EXECUTION_PLAN.md`.

## Fase ativa

**F01 — Experiência e arquitetura da primeira versão**

**Subetapa atual:** F01.01 — mapear Home, Nova ideia, Projeto, Entrevista, Decisões, Escopo, Spec, Roadmap e Exportação.

**Objetivo:** produzir um fluxo navegável revisável e definir as fronteiras técnicas do próprio app antes de criar o skeleton Android.

## Resultado esperado

Fluxo, estados e arquitetura documentados de forma verificável, com domínio independente de Android e contratos portáteis preservados.

## Trabalho autorizado agora

- mapear cada tela de F01.01, seu objetivo, entrada, saída e ação principal;
- criar ou atualizar artefatos documentais e wireframes de F01;
- avançar pelas subetapas F01.01–F01.08 somente quando dependências e critérios do `EXECUTION_PLAN.md` estiverem satisfeitos;
- definir navegação, recuperação de rascunho, fronteiras arquiteturais, portas e ADRs previstos em F01;
- fixar aparelhos, minSdk e toolchain somente após consultar documentação oficial vigente;
- executar verificações documentais e arquiteturais proporcionais;
- atualizar `PROJECT_STATE.md` e `EXECUTION_PLAN.md` quando o estado realmente mudar.

## Não autorizado

- criar projeto Android, código Compose, Room, Gradle ou integração real com provider;
- iniciar F02 antes de `G01 = PASS` registrado pela Product Authority;
- implementar F11–F20 / Idea Factory enquanto `G10 != PASS`;
- tratar a baseline de contexto como implementação de F15;
- alterar D01–D09 sem decisão explícita da Product Authority;
- criar segundo target, backend, sincronização ou abstração multiplataforma prematura;
- executar ação externa/destrutiva não necessária à fase.

## Decisões e gates vigentes

- D01–D09: `LOCKED` desde 13/09/2026;
- G00: `PASS` registrado em 14/09/2026 após auditoria independente;
- G01: `NOT_RUN`;
- G10: `NOT_RUN`;
- Idea Factory/F11–F20: bloqueada.

`NOT_RUN != PASS`. O Builder não registra o próprio gate.

## Subetapas e critérios

1. **F01.01 — Mapa de telas:** cada tela possui objetivo e ação principal.
2. **F01.02 — Wireframes:** fluxo principal e estados vazio, erro, offline e interrompido são revisáveis.
3. **F01.03 — Navegação e rascunho:** salvar, voltar e recuperar não descartam entrada silenciosamente.
4. **F01.04 — Arquitetura:** módulos e portas de IA, exportação e persistência mantêm domínio independente de Android/provider.
5. **F01.05 — Ambiente alvo:** aparelhos, minSdk e toolchain são definidos com documentação vigente e build de exemplo verificável somente na fase autorizada.
6. **F01.06 — Autoridade de LOCK:** protótipo distingue sugestão, hipótese aceita e decisão humana fechada.
7. **F01.07 — Portabilidade:** ADR preserva interfaces para persistência, arquivos, IA e segurança.
8. **F01.08 — Contrato portátil:** `project.json`/`manifest.json` e compatibilidade de schema não dependem de UI/Room.

## Gate G01

**Estado:** `NOT_RUN`.

Critério: fluxo aprovado para implementação; modelo de custódia de credenciais e armazenamento resolvido, com provider específico homologado somente em F04.

## Retorno seguro

Se F01 ou G01 falhar, revisar wireframes, contratos e ADRs. Não há implementação nem dados de usuário a migrar nesta fase.

## Próxima ação

Executar F01.01 e produzir o mapa revisável das telas e ações principais. Não iniciar Android.
