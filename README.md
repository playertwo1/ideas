# Idea

**Idea** é um aplicativo Android local-first para transformar uma ideia incompleta em um pacote de especificação coerente, verificável e pronto para execução por pessoas ou agentes de programação.

> **Regra de ouro:** não queremos uma máquina de gerar documentos. Queremos uma máquina de reduzir ambiguidade antes que uma IA escreva código.

## Visão

O Idea preserva a intenção original, ajuda a esclarecer lacunas, registra decisões humanas separadamente de sugestões da IA, corta o MVP, cria requisitos verificáveis, organiza o roadmap e exporta um pacote versionado. Em versões posteriores, poderá pesquisar evidências, gerar blueprints, decompor tarefas, compilar contexto, gerar guardrails, revisar o pacote e preparar o handoff.

A fronteira do produto é clara: **o Idea termina no handoff**. Execução contínua de agentes, acompanhamento do código e Mission Control pertencem ao Projeto Vivo ou a ferramentas externas.

## Arquitetura de produto: Core primeiro, Factory depois

### Idea Core — provar valor

`Capture → Clarify → Decide → Cut → Specify → Plan → Export`

O Core corresponde ao caminho F00–F10 do [ROADMAP](ROADMAP.md). O objetivo do MVP 0.1 é provar que um pacote exportado permite entender o que construir, o que ficou fora e como verificar o resultado sem reconstruir o contexto da conversa.

### Idea Factory — ampliar um núcleo já útil

`Research → Validate → Blueprint → Task DAG → Context Compiler → Guardrails → Independent Review → Bootstrap → Agent Handoff`

A Factory corresponde a F11–F20. **Ela não deve ser implementada enquanto G10 não passar.** G10 é o Go/No-Go que comprova que o Core já reduz ambiguidade e gera valor.

## Modos de profundidade

- **LIGHT:** utilitário, experimento ou alteração pequena; processo curto e proporcional.
- **STANDARD:** aplicativos e features normais; entrevista, validação, spec, MVP e roadmap.
- **DEEP:** sistemas amplos, sensíveis, multiagente ou com integrações relevantes; usa as capacidades avançadas quando disponíveis.

Selecionar LIGHT nunca remove validações de segurança aplicáveis. DEEP não pode ser anunciado como concluído antes de seus gates existirem.

## Princípios

- Intenção original e interpretação da IA são entidades separadas.
- Decisões humanas, sugestões da IA, hipóteses e evidências externas permanecem distinguíveis.
- IDs, locks, dependências, integridade e gates usam regras determinísticas.
- `NOT_RUN` nunca significa `PASS`.
- Readiness é baseado em checks, não em percentuais cosméticos de confiança.
- Profundidade cresce com risco/complexidade.
- Regeneração da IA não sobrescreve edição humana aceita.
- Mudanças em decisão `LOCKED` criam revisão e análise de impacto.
- Cada requisito/tarefa relevante registra `rationale`: por que existe.
- Documentação deve reduzir ambiguidade, não aumentar burocracia.

## Progressive Commitment

Informações evoluem proporcionalmente à autoridade necessária:

`CAPTURED → SUGGESTED → ACCEPTED → LOCKED → IMPLEMENTATION_RELEVANT`

A IA pode propor; somente autoridade humana apropriada fecha decisões que mudam produto ou escopo.

## Como estamos construindo o Idea

- **Product Authority:** usuário — decide produto, escopo e novos riscos/decisões materiais.
- **Builder:** Antigravity — executa autonomamente o que a fase ativa já autorizou.
- **Auditor:** Codex — verifica diff, testes, acceptance, regressões, escopo, segurança e evidências com contexto separado.

**Autonomia proporcional:** não pedir nova autorização para executar algo já autorizado. Escalar apenas decisão nova, risco novo, mudança material de escopo, ação externa/destrutiva não autorizada ou conflito não resolvido pelos contratos.

Documentação operacional:

- `ROADMAP.md` v0.6 — contrato canônico e decisões.
- `PHASE_CURRENT.md` — autorização operacional da fase ativa.
- `EXECUTION_PLAN.md` — sequência/checklist.
- `PROJECT_STATE.md` — estado curto para retomada.
- `AGENTS.md` + `WATCHDOG.md` — comportamento, segurança e anti-drift.
- `AUDIT.md` — revisão independente.
- `REFERENCE_MATRIX.md` — consolidação das referências estudadas.

O DOCX original é fonte histórica de pesquisa, não autoridade operacional.

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

O Idea não é uma cópia de nenhum desses projetos. A combinação desejada é:

1. **Intake e funil de especificação** do AI App Idea Generator.
2. **Decisões Propose/Refine/Lock** do Vibe Architect.
3. **Capacidades modulares** inspiradas no BuilderOS.
4. **Rastreabilidade e constituição comum** inspiradas no Spec-Driven Development.
5. **Contexto compilado e consistência** inspirados no SpecD.
6. **Clarificação e revisão estruturada** inspiradas no AI PRD Generator.
7. **Specs locais e limites explícitos** inspirados no SpecDD.
8. **Plan/Build/Verify, DAG e separação Builder/Auditor** inspirados no pb-spec.

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

Roadmap canônico v0.6. Fase ativa F00/F00.01; G00 e G10 `NOT_RUN`; D01–D09 `LOCKED`. A implementação Android ainda não está autorizada.
