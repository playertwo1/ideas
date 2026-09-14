# ARTIFACT_CATALOG — M0

Este catálogo materializa F00.04. `ROADMAP.md` continua canônico. O catálogo não cria novas features; define quais artefatos e verificações representam o contrato já aprovado.

## Estados

- `REQUIRED`: obrigatório para o gate/versão aplicável.
- `CONDITIONAL`: obrigatório quando risco/capacidade indicada se aplica.
- `FUTURE`: pertence a versão posterior; não pode bloquear 0.1 enquanto não aplicável.
- `N_A`: somente com rationale explícito.

`NOT_RUN != PASS`. Um artefato existir não significa que seu gate passou.

## Idea Core — MVP 0.1

| Artefato/capacidade | LIGHT | STANDARD | DEEP em 0.1 | Gate principal | Regra |
|---|---|---|---|---|---|
| Ideia original preservada | REQUIRED | REQUIRED | REQUIRED | Core | Nunca substituída pela interpretação |
| Interpretação separada | REQUIRED | REQUIRED | REQUIRED | Core | IA não altera o original |
| Lacunas/perguntas críticas | proporcional | REQUIRED | REQUIRED | Core | risco pode elevar profundidade |
| Decisões + revisões | REQUIRED quando houver decisão | REQUIRED | REQUIRED | Core | IA não promove para LOCKED |
| Hipótese + teste mínimo | REQUIRED | REQUIRED | REQUIRED | Core | ficha curta aprovada em D04 |
| Corte MUST/SHOULD/COULD/LATER/REJECTED | REQUIRED | REQUIRED | REQUIRED | Core | MUST tem rationale |
| REQ/NFR + acceptance | REQUIRED para MUST | REQUIRED | REQUIRED | Core | zero MUST sem cobertura |
| Roadmap editável | REQUIRED | REQUIRED | REQUIRED | Core | fases têm objetivo/verify/dependências |
| Readiness/checks estruturais | REQUIRED | REQUIRED | REQUIRED | Core | NOT_RUN visível |
| Export Markdown + JSON + ZIP | REQUIRED | REQUIRED | REQUIRED | PKG-01–06 | D06 |
| Restore do próprio pacote | REQUIRED | REQUIRED | REQUIRED | PKG-01–06 | round-trip preserva IDs/relações |
| Histórico/rastreabilidade | proporcional | REQUIRED | REQUIRED | Core | autoria distinguível |
| Segurança aplicável | REQUIRED | REQUIRED | REQUIRED | Security | LIGHT não desliga segurança |

## Idea Factory — não implementável antes de G10

Pesquisa automática, Validator completo, UX/Architecture Blueprint gerado, Task DAG, Context Compiler, Guardrail Generator, Independent Review completo, análise de impacto completa, Repository Bootstrap e Agent Handoff avançado são `FUTURE` para o MVP 0.1. Selecionar DEEP pode registrar que serão necessários futuramente, mas não os transforma em capacidades presentes.

## Pacote mínimo 0.1

Para um pacote 0.1 ser considerado núcleo revisado pronto para exportação, os gates PKG-01–PKG-06 aplicáveis precisam estar satisfeitos conforme o `ROADMAP.md`. Pacotes rascunho podem ser exportados com pendências visíveis; não recebem estado de handoff completo.

## G00 — contrato antes de código

G00 só pode ser registrado como PASS quando, no mínimo:

1. F00.01–F00.08 estiverem materializadas;
2. `PROJECT_STANDARD.md` existir e estiver coerente com D01–D09;
3. schemas e exemplos/fixtures estiverem disponíveis e verificáveis;
4. modos e artefatos não prometerem Factory no 0.1;
5. Core/Factory e G10 estiverem formalizados;
6. portabilidade Android-first sem overengineering estiver formalizada;
7. Auditor tiver emitido `AUDIT RESULT: PASS` sem finding bloqueante;
8. Product Authority registrar explicitamente o resultado final de G00.

A aprovação humana não converte check técnico não executado em PASS; `AUDIT RESULT: PASS` sozinho também não troca a fase.
