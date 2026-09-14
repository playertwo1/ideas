# Idea

**Idea** é um aplicativo Android local-first para transformar uma ideia incompleta em um pacote de especificação coerente, verificável e pronto para execução por pessoas ou agentes de programação.

> **Regra de ouro:** não queremos uma máquina de gerar documentos. Queremos uma máquina de reduzir ambiguidade antes que uma IA escreva código.

## Visão

O Idea preserva a intenção original, ajuda a esclarecer lacunas, registra decisões humanas separadamente de sugestões da IA, corta o MVP, cria requisitos verificáveis, organiza o roadmap e exporta um pacote versionado. Em versões posteriores, poderá pesquisar evidências, gerar blueprints, decompor tarefas, compilar contexto, gerar guardrails, revisar o pacote e preparar o handoff.

A fronteira do produto é clara: **o Idea termina no handoff**. Execução contínua de agentes, acompanhamento do código e Mission Control pertencem ao Projeto Vivo ou a ferramentas externas.

## Arquitetura de produto: Core primeiro, Factory depois

### Idea Core — provar valor

`Capture → Clarify → Decide → Cut → Specify → Plan → Export`

O Core corresponde ao caminho F00–F10 do [ROADMAP](ROADMAP.md). O objetivo do MVP 0.1 é provar que um pacote exportado permite entender o que construir, o que ficou fora e como verificar o resultado sem reconstruir uma conversa.

### Idea Factory — somente depois de provar o Core

`Research → Validate → Blueprint → Task DAG → Context → Guardrails → Review → Bootstrap`

A Factory corresponde a F11–F20. **G10 é um Go/No-Go obrigatório:** F11+ não deve ser implementado enquanto o piloto do Core não demonstrar confiabilidade e redução útil de ambiguidade. Se G10 falhar, o trabalho retorna principalmente a F06–F08.

## Como desenvolver este projeto

A documentação deve permitir que uma IA nova entenda o projeto sem depender do histórico das conversas.

- **README.md** — entrada e visão rápida.
- **ROADMAP.md** — mapa mestre, fases, gates, requisitos, referências e critérios.
- **NORTH_STAR.md** — visão curta e princípios imutáveis, quando criado.
- **PHASE_CURRENT.md** — contrato operacional da fase ativa, quando criado.

### Papéis de execução

- **Antigravity = Builder:** implementa autonomamente tudo que estiver explicitamente autorizado pela fase ativa.
- **Codex = Auditor:** verifica diff, testes, acceptance, escopo, regressões e evidências; não refaz o trabalho do Builder por padrão.
- **Usuário = Product Authority:** decide mudanças de produto, novos riscos materiais, mudanças de escopo, ações externas/destrutivas não autorizadas e conflitos não resolvidos.

**Autonomia proporcional:** não pedir nova autorização para executar algo que já esteja explicitamente autorizado. Escalar somente decisão nova, risco novo ou mudança material de escopo.

## Referências GitHub que influenciaram o projeto

As referências abaixo foram estudadas para extrair padrões, formatos e fluxos. **São inspiração, não dependências obrigatórias.** Reuso de código exige revisão da licença/proveniência vigente e registro em `REFERENCE_MATRIX.md`.

| Projeto | Link | O que inspirou no Idea | Onde entra | O que não copiar/adotar cegamente |
|---|---|---|---|---|
| AI App Idea Generator | https://github.com/Enterprise-DNA-OS/ai-app-idea-generator | Funil ideia → perguntas → spec, intake e plano de MVP | F04/F05 | Arquitetura web/Supabase como requisito do nosso Android local-first |
| Vibe Architect | https://github.com/mohdhd/vibe-architect | Propose → Refine → Lock, opções concretas, trade-offs e export | F01/F06/F09 | Acoplamento React/UI específica |
| BuilderOS | https://github.com/BuildGreatProducts/builder-os | Skills independentes/encadeáveis: ideação, validação, planning, build/launch | F00/F06/F07/F12 | Execução automática do MVP dentro do Idea Core |
| Spec-Driven Development | https://github.com/agentgill/spec-driven-development | Requirements/design/tasks, IDs, acceptance e constituição compartilhada entre agentes | F07/F08/F13/F14/F16 | Limitar o Idea a apenas três documentos |
| SpecD | https://github.com/specd-sdd/SpecD | Context Compiler, verification/approval e análise de impacto | F04/F08/F13/F15/F18 | Grafo completo spec↔código no MVP |
| AI PRD Generator | https://github.com/cdeust/ai-prd-generator | Clarificação, tipos de PRD/projeto, rastreabilidade e revisão | F07/F12/F17 | Percentual de confiança tratado como verdade; partes licenciadas sem análise |
| SpecDD | https://github.com/specdd/specdd | Specs pequenas e locais, ownership, limites e invariantes | F01/F13/F15/F16/F18 | Fragmentar tudo em centenas de specs sem visão global |
| pb-spec | https://github.com/longcipher/pb-spec | Plan → Build → Verify, DAG, task sizing e Generator/Evaluator Isolation | F14/F16/F17 | TDD/mutation testing pesado obrigatório em qualquer projeto |

### Como essas ideias se combinam

O Idea não é uma cópia de nenhum desses projetos. A combinação desejada é: intake e funil de especificação do AI App Idea Generator; decisões Propose/Refine/Lock do Vibe Architect; capacidades modulares inspiradas no BuilderOS; rastreabilidade e constituição comum do Spec-Driven Development; contexto compilado e consistência do SpecD; clarificação e revisão estruturada do AI PRD Generator; specs locais e limites explícitos do SpecDD; e Plan/Build/Verify, DAG e separação Builder/Auditor do pb-spec.

A decisão final de design sempre pertence ao Idea e deve privilegiar Android-first, local-first, simplicidade proporcional e independência de provedor.

**Regra para agentes:** nenhum `REF-xx` substitui uma especificação. Uma IA deve conseguir implementar a fase usando os contratos do Idea sem consultar o repositório externo. A referência explica a origem da ideia; requisitos, acceptance, política canônica e fase ativa definem o comportamento.

## MVP 0.1

Inclui: projetos locais; captura da ideia original; interpretação separada; modos LIGHT/STANDARD/DEEP solicitado; Smart Interview; decisões versionadas; hipótese/teste mínimo; MVP Cutter; REQ/NFR + acceptance; roadmap básico; readiness por checks; exportação Markdown/JSON/ZIP; restauração; histórico e proteção de credenciais.

Fora do MVP 0.1: execução de Codex/Claude/Antigravity pelo app; Git remoto; criação automática de repositório; pesquisa web profunda; Task DAG completo; Context Compiler; Guardrail Generator; revisão independente completa; Mission Control; grafo spec↔código; JIRA; Figma e roteamento automático de modelos.

## Roadmap

O plano detalhado, gates, requisitos, NFRs, riscos e critérios de aceite estão em [ROADMAP.md](ROADMAP.md).

**Regra de avanço:** F11+ só começa quando G10 = `PASS`.

## Fonte da verdade

O estado estruturado local será a fonte operacional. Markdown é materialização de uma revisão. O pacote exportado inclui `project.json` e `manifest.json`, com IDs, hashes, capabilities, resultados de gates e limitações conhecidas.

## Status

Projeto em fase de especificação/validação do roadmap. A implementação Android ainda não deve assumir decisões de produto que permaneçam abertas no roadmap.
