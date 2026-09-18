# Design: migração agressiva do Idea para o Gold Standard

## Objetivo

Migrar o repositório `ideas` para o trilho operacional do Gold Standard,
reduzindo fontes duplicadas e tornando validação, evidência e contexto
reproduzíveis, sem transformar o projeto em um clone do `ideias_standard`.

## Estado preservado

- `F01` continua sendo a fase ativa.
- `F01.08` continua concluída pelo Builder e aguardando auditoria.
- `G00 = PASS`, `G01 = NOT_RUN` e `G10 = NOT_RUN` permanecem inalterados.
- `D01–D09` continuam `LOCKED`.
- F02/Android, F11–F20 e o Runner permanecem fora do escopo.

## Arquitetura documental

- `PROJECT_STATE.md` será a fonte única do status dinâmico.
- `ROADMAP.md` será o contrato de fases, critérios e dependências.
- `README.md` explicará propósito, fluxo Gold e entrada de validação, sem
  duplicar o status atual.
- `AGENTS.md` permanecerá curto e operacional.
- Contexto adicional será roteado por `AI_CONTEXT_INDEX.md` e
  `context-manifest.json`.

## Componentes Gold

1. Manifesto e validação reproduzível na raiz.
2. Fixtures válida e defeituosa para provar PASS/FAIL.
3. Evidência versionada com digest dos artefatos relevantes.
4. Golden Diff documentando o que foi adotado, rejeitado ou mantido.
5. Dogfooding do próprio repositório e do exemplo Gold.

Nenhum componente será adicionado apenas por simetria com o template; cada
item deve ter uso verificável no projeto.

## Fluxo de migração

1. Registrar a baseline e inventariar documentos e contratos atuais.
2. Produzir Golden Diff com incompatibilidades e decisões.
3. Consolidar documentação e estrutura Gold.
4. Executar check, self-check, testes e fixtures.
5. Atualizar evidência e estado sem registrar aprovação de gate.
6. Submeter o resultado a auditoria independente.

## Limites e falhas

- Não iniciar F02 nem alterar o gate G01.
- Não promover conteúdo importado ou sugestão para decisão `LOCKED`.
- Falhar fechado em manifestos inválidos, caminhos ausentes ou evidência
  inconsistente.
- Não remover artefatos de produto sem registrar a razão no Golden Diff.

## Critérios de aceitação

- README, ROADMAP, PROJECT_STATE e AGENTS não se contradizem.
- A validação reproduzível passa na raiz e nas fixtures.
- O próprio `ideas` e o exemplo Gold passam pelo fluxo dogfooded.
- Evidências apontam para arquivos existentes e têm digest verificável.
- O estado preserva F01/G00/G01 e não registra gate ou aprovação automática.
