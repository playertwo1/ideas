# Idea

**Idea** é um aplicativo Android local-first para transformar uma ideia incompleta em um pacote de especificação coerente, verificável e pronto para execução por pessoas ou agentes de programação.

> **Regra de ouro:** não queremos uma máquina de gerar documentos. Queremos uma máquina de reduzir ambiguidade antes que uma IA escreva código.

## Visão

O Idea preserva a intenção original, ajuda a esclarecer lacunas, separa sugestões da IA de decisões humanas, corta o MVP, cria requisitos verificáveis, organiza o roadmap e exporta um pacote versionado.

A fronteira do produto é clara: **o Idea termina no handoff**. Execução contínua de agentes, acompanhamento de código e Mission Control pertencem ao Projeto Vivo ou a ferramentas externas.

## Estado atual

F06.01–F06.07 estão implementadas e aguardam auditoria independente. `G05 =
PASS`, `G06 = NOT_RUN` e F07 não foi iniciada. Nenhuma destas marcações equivale
a aprovação automática; a autorização de avanço permanece com a Product
Authority.

## Arquitetura de produto

### Idea Core — provar valor

`Capture → Clarify → Decide → Cut → Specify → Plan → Export`

Corresponde a F00–F10. O MVP 0.1 deve provar que um pacote exportado permite entender o que construir, o que ficou fora e como verificar o resultado sem reconstruir a conversa original.

### Idea Factory — ampliar um núcleo já útil

`Research → Validate → Blueprint → Task DAG → Context Compiler → Guardrails → Independent Review → Bootstrap → Agent Handoff`

Corresponde a F11–F20. **Não pode entrar em implementação antes de `G10 = PASS`.**

## Modos

- **LIGHT:** utilitário, experimento ou alteração pequena; processo proporcional.
- **STANDARD:** aplicativos/features normais; entrevista, validação, spec, MVP e roadmap.
- **DEEP:** sistemas amplos, sensíveis, multiagente ou com integrações relevantes; capacidades avançadas entram somente quando realmente suportadas.

Segurança aplicável deriva do risco, não apenas do modo escolhido.

## Princípios

- intenção original e interpretação da IA são entidades separadas;
- decisão humana, sugestão, hipótese e evidência externa permanecem distinguíveis;
- IDs, locks, dependências, integridade e gates usam regras determinísticas;
- `NOT_RUN` nunca significa `PASS`;
- readiness é baseado em checks, não em percentuais cosméticos;
- profundidade cresce com risco/complexidade;
- regeneração da IA não sobrescreve edição humana aceita;
- mudança `LOCKED` cria revisão e análise de impacto;
- itens materiais registram `rationale`;
- documentação deve reduzir ambiguidade, não aumentar burocracia;
- agentes devem receber **minimum sufficient context**, não o repositório inteiro por padrão.

## Progressive Commitment

`CAPTURED → SUGGESTED → ACCEPTED → LOCKED → IMPLEMENTATION_RELEVANT`

A IA pode propor; somente autoridade humana apropriada fecha decisões materiais de produto/escopo.

## Papéis de desenvolvimento

- **Product Authority:** usuário — produto, escopo e decisões materiais.
- **Builder:** Antigravity — executa o contrato autorizado e produz evidências.
- **Auditor:** Codex — tenta falsificar a solução, verificando diff, acceptance, regressões, segurança e evidências com contexto independente.

Autonomia é proporcional: não pedir novamente autorização para trabalho já autorizado; escalar somente decisão nova, risco novo, mudança material, ação irreversível/destrutiva não autorizada ou conflito canônico real.

## Documentação operacional

Para retomar o projeto: leia `AGENTS.md` e `PROJECT_STATE.md`; a autorização e os limites da fase estão em `PHASE_CURRENT.md`. Abra `ROADMAP.md` e os contratos específicos somente para a tarefa em mãos.

Da raiz do repositório, execute `python scripts/check.py` para validar contratos e rodar os testes existentes. O comando retorna código não zero em falha. Para o aplicativo Android, use o Gradle Wrapper e os testes indicados em `PHASE_CURRENT.md` e na evidência da fase ativa.

- `AGENTS.md` — contrato mínimo para agentes;
- `PROJECT_STATE.md` — **única fonte de status operacional dinâmico**;
- `PHASE_CURRENT.md` — autorização e limites da fase atual;
- `EXECUTION_PLAN.md` — checklist completo;
- `ROADMAP.md` — contrato canônico e decisões;
- `CONTEXT_POLICY.md` — política de eficiência de contexto;
- `AI_CONTEXT_INDEX.md` + `context-manifest.json` — roteamento de contexto por tarefa;
- `WATCHDOG.md` — segurança/anti-drift carregado por gatilho;
- `AUDIT.md` — método de auditoria independente;
- `REFERENCE_MATRIX.md` — referências externas, adoção e limites.

O DOCX original é fonte histórica de pesquisa, não autoridade operacional.

## Contexto mínimo para agentes

Bootstrap padrão:

1. `AGENTS.md`;
2. `PROJECT_STATE.md`;
3. pedido atual.

Depois o agente usa `AI_CONTEXT_INDEX.md`/`context-manifest.json` para ampliar contexto somente quando necessário. ROADMAP completo, WATCHDOG, AUDIT, histórico e pesquisa externa não são leitura padrão.

A política detalhada está em `CONTEXT_POLICY.md`.

## MVP 0.1

Inclui projetos locais, captura da ideia original, interpretação separada, modos LIGHT/STANDARD/DEEP, Smart Interview, decisões versionadas, hipótese/teste mínimo, MVP Cutter, REQ/NFR + acceptance, roadmap básico, readiness por checks, exportação Markdown/JSON/ZIP, restauração, histórico e proteção de credenciais.

Fora do MVP 0.1: execução de agentes pelo app, Git remoto, criação automática de repositório, pesquisa web profunda, Task DAG completo, Context Compiler, Guardrail Generator, revisão independente completa, Mission Control, grafo spec↔código, JIRA, Figma e roteamento automático de modelos.

## Referências externas

As referências GitHub usadas na concepção são inspiração, não dependências. A matriz canônica de adoção/proveniência está em `REFERENCE_MATRIX.md`.

Nenhum `REF-xx` substitui especificação interna. Um agente deve conseguir implementar uma fase usando contratos do Idea sem precisar consultar o repositório externo de referência.

## Fonte da verdade

O estado estruturado local será a fonte operacional do produto. Markdown é materialização de uma revisão. O pacote exportado inclui `project.json` e `manifest.json`, com IDs, hashes, capabilities, gates e limitações conhecidas.

## Trilho Gold

Este repositório usa o Gold Standard como trilho de trabalho, não como um
orquestrador de agentes. `PROJECT_STATE.md` guarda o status atual; `ROADMAP.md`
guarda fases e critérios; `PHASE_CURRENT.md` autoriza o trabalho vigente.
Da raiz, `python scripts/check.py` executa a validação reproduzível. O
manifesto, as fixtures e `docs/GOLDEN_DIFF.md` documentam a composição adotada.
F01 permanece ativa; Android/F02 continua bloqueado até `G01 = PASS`.

## Estado atual

Não duplique status aqui. **Consulte `PROJECT_STATE.md`.**
