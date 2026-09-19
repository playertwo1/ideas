# Roadmap — Idea MVP e evolução

Este é o contrato canônico de produto. Ele define fronteira, decisões, fases,
dependências e gates. O estado operacional está em `PROJECT_STATE.md`, a
autorização atual em `PHASE_CURRENT.md` e o checklist executável em
`EXECUTION_PLAN.md`.

O roadmap histórico foi arquivado em
[`docs/archive/ROADMAP_V0.6_LEGACY.md`](docs/archive/ROADMAP_V0.6_LEGACY.md) e
serve apenas como referência documental; não autoriza trabalho atual.

## 1. Resultado e fronteira

O Idea transforma uma ideia incompleta em um pacote de especificação coerente,
verificável e pronto para handoff.

```text
Capture → Clarify → Decide → Cut → Specify → Plan → Export → Handoff
```

O Idea termina no handoff. Execução contínua de agentes, acompanhamento de
código, Mission Control e operação do Projeto Vivo ficam fora do produto.

### Idea Core

F00–F10 provam o MVP 0.1: captura, interpretação, entrevista, decisões,
hipótese mínima, recorte de MVP, requisitos, roadmap, exportação e piloto.

### Idea Factory

F11–F20 ampliam um Core útil: pesquisa, validação completa, blueprints, tarefas,
contexto, guardrails, revisão, bootstrap e handoff.

**Regra G10:** F11–F20 só podem entrar em implementação com `G10 = PASS`
registrado pela Product Authority. `NOT_RUN` não libera a Factory.

## 2. Regras invariáveis

- preservar a intenção original e mantê-la distinta da interpretação da IA;
- distinguir sugestão, hipótese, decisão humana e evidência externa;
- usar regras determinísticas para IDs, referências, dependências, locks e gates;
- permitir ler, editar, salvar, conferir e exportar offline; IA pode ser online;
- não transformar conteúdo importado em comando executável;
- não permitir que IA defina ID definitivo, autorização, `LOCKED` ou `PASS`;
- regeneração não sobrescreve edição humana aceita;
- mudança de decisão LOCKED cria revisão, motivo, impacto e invalidação de derivados;
- domínio e contratos não dependem diretamente de Android ou provider;
- evolução não deve ampliar o MVP sem decisão explícita.

## 3. Estado vigente

Em 19/09/2026:

- fase ativa: F09 — Exportação Markdown e restauração;
- F09.01 implementada e aguardando auditoria independente;
- F09.02+ e F10 não iniciadas;
- `G09 = NOT_RUN` e `G10 = NOT_RUN`;
- Idea Factory/F11–F20 bloqueada;
- D01–D09 `LOCKED`.

`PROJECT_STATE.md` é a fonte do estado real. Este resumo não autoriza avanço.

## 4. Marcos e dependências

| Marco | Fases | Resultado | Gate |
|---|---|---|---|
| M0 — Contrato | F00–F01 | padrão, corte e arquitetura revisáveis | G00 |
| M1 — Offline | F02–F03 | app instalável, persistência e recuperação | G02/G03 |
| M2 — MVP 0.1 | F04–F10 | Core completo e piloto | G10 Go/No-Go |
| M3 — Factory inicial | F11–F13 | pesquisa, validação e blueprints | G11–G13 |
| M4 — Decomposição | F14–F16 | tarefas, contexto e guardrails | G14–G16 |
| M5 — Consistência | F17–F18 | revisão e mudança com impacto | G17/G18 |
| M6 — Bootstrap | F19 | pacote inicial reproduzível | G19 |
| M7 — 1.0 | F20 | Projeto Vivo e handoff real | G20 |

Dependência principal:

```text
F00 → F01 → F02 → F03 → F04/F05 → F06 → F07 → F08 → F09 → F10
                                                               │
                                                               └─ G10 PASS → F11 → F12 → F13 → F14 → F15/F16 → F17 → F18 → F19 → F20
```

F04 e F05 podem avançar em paralelo após os contratos de F03. F15 e F16 podem
avançar em paralelo após F14. Preparação documental não equivale a autorização
de implementação.

## 5. Fases do produto

O detalhe de subetapas e checks está em `EXECUTION_PLAN.md`; esta tabela é o
mapa operacional resumido.

| Fase | Entrega | Verificação de prontidão | Dependência |
|---|---|---|---|
| F00 | padrão, schemas, fixtures, gates e corte 0.1 | contratos e fixtures auditáveis | — |
| F01 | telas, wireframes, navegação e arquitetura | fluxo revisável e Core independente | F00 |
| F02 | skeleton Android, navegação e checks | APK debug e checks locais | G01 |
| F03 | persistência, revisões, drafts e backup | reinício/falha não perdem dados confirmados | F02 |
| F04 | contrato IA, fake, provider, credencial e falhas | geração estruturada controlada | F03 |
| F05 | captura, snapshot original e interpretação | intenção preservada e editável | F03/F04 |
| F06 | entrevista adaptativa e decisões versionadas | LOCK, reabertura e autoria rastreáveis | F05 |
| F07 | hipótese, MVP Cutter e requisitos | MUST ligados a acceptance e teste | F06 |
| F08 | fases, dependências e readiness | roadmap sem órfãos, ciclos ou refs quebradas | F07 |
| F09 | Markdown/JSON/ZIP, hashes e restauração | round-trip preserva IDs e relações | F08 |
| F10 | piloto e estabilização do MVP 0.1 | pacote útil, compreensível e confiável | F09 |
| F11 | pesquisa assistida com fontes e claims | afirmações materiais rastreáveis | G10 |
| F12 | Idea Validator completo | BUILD/PIVOT/PARK/REJECT com evidência | F11 |
| F13 | blueprints de UX, arquitetura, dados e segurança | pacote técnico coerente | F12 |
| F14 | Task DAG e compilador de tarefas | tarefas pequenas, ordenáveis e verificáveis | F13 |
| F15 | Context Compiler | contexto mínimo íntegro e versionado | F14 |
| F16 | guardrails e pacote de instruções | limites explícitos sem ampliar autoridade | F13/F14 |
| F17 | revisão independente do pacote | findings reproduzíveis e resolvíveis | F15/F16 |
| F18 | consistência semântica e housekeeping | derivados coerentes e stale visível | F17 |
| F19 | bootstrap controlado | perfil homologado e destino explícito | F18 |
| F20 | dogfood com Projeto Vivo e release 1.0 | primeiro handoff real validado | F19 |

## 6. Contrato de dados e autoridade

O estado estruturado persistido é a fonte operacional. Markdown, JSON e ZIP são
materializações versionadas; o pacote representa um único snapshot consistente.
Edição externa não é importada silenciosamente em 0.1.

Entidades materiais incluem projeto, snapshot, interpretação, pergunta/resposta,
decisão/revisão, validação, requisito, feature/escopo, fase/roadmap, artefato,
evento, geração, gate, exportação, fonte/claim, tarefa, review e finding. As
coleções e relações devem ter IDs estáveis, origem, rationale e revisão quando
aplicável.

Estados essenciais:

- decisão: `PROPOSED → REFINING → LOCKED`, com `SUPERSEDED` para revisão antiga;
- artefato: `DRAFT → REVIEWED → APPROVED`, com `STALE` após mudança de entrada;
- geração: `QUEUED → RUNNING → SUCCEEDED | FAILED | CANCELLED | INTERRUPTED`;
- gate: `PASS | FAIL | NOT_RUN | NOT_APPLICABLE`, sendo rationale obrigatório no último;
- validação: `BUILD | PIVOT | PARK | REJECT`, com evidência separada;
- prontidão: `DRAFT | READY_FOR_REVIEW | READY_FOR_HANDOFF`.

Product Authority decide produto, escopo e mudanças LOCKED. Builder executa o
escopo autorizado. Auditor revisa independentemente. Automação não registra
gate humano nem muda de fase sozinha.

## 7. Exportação e portabilidade

O pacote canônico contém, quando suportado, `project.json`, `manifest.json`,
Markdown, versão, revisão, hashes e limitações conhecidas. `project.json` e
`manifest.json` devem ser interpretáveis sem UI, Room ou provider específico.

O Core permanece independente de Android; Android, Room, armazenamento, arquivos,
credenciais e provider são adapters. Desktop/Web e framework multiplataforma só
podem ser avaliados depois de G10, sem ampliar o MVP 0.1.

## 8. Escopo fora do MVP 0.1

Ficam fora: execução de agentes, Git remoto, criação automática de repositório,
pesquisa web automática, grafo spec↔código, colaboração multiusuário,
sincronização entre dispositivos, clientes Web/Desktop, backend compartilhado
sem necessidade, faturamento, JIRA, Figma, voz, sandbox de código gerado e
seleção automática de modelos.

## 9. D01–D09 — decisões LOCKED

As decisões abaixo foram aprovadas pela Product Authority em 13/09/2026. Alterar
qualquer uma exige novo ID/versão, rationale, impacto e decisão explícita.

| ID | Decisão vigente |
|---|---|
| D01 | Público inicial pessoal/time pequeno; piloto por APK; Android-first. |
| D02 | IA provider-agnostic; fake obrigatório em testes e um provider real homologado no MVP; ferramentas de agentes não são dependências do Core. |
| D03 | Ler, editar, salvar, conferir e exportar offline; IA pode exigir rede. |
| D04 | MVP 0.1 mantém núcleo do documento e ficha curta de hipótese/teste. |
| D05 | DEEP pode ser escolhido, mas capacidades futuras continuam explicitamente pendentes. |
| D06 | Markdown/ZIP + JSON e restauração do próprio pacote; edição canônica no app. |
| D07 | Milestones sem data rígida; recalibrar após persistência e IA. |
| D08 | Primeiro Projeto Vivo DEEP após pilotos; bootstrap Android homologado. |
| D09 | Android-first no MVP, Core platform-agnostic; Desktop/Web somente após G10. |

## 10. Protocolo de execução

```text
pedido → localizar contexto → planejar → alterar → validar → revisar diff → auditar
```

Para cada subetapa: executar checks aplicáveis, registrar evidência, manter o
estado coerente e aguardar auditoria/gate quando exigido. Uma auditoria PASS não
substitui o registro da Product Authority.

## 11. Próxima ação

Seguir `PROJECT_STATE.md` e `PHASE_CURRENT.md`: F09.01 está implementada e
aguarda auditoria independente. Não iniciar F09.02+, não registrar `G09` e não
iniciar F11–F20 enquanto `G10 != PASS`.
