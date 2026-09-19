# Idea

O **Idea** é um aplicativo Android local-first que reduz ambiguidade antes de
uma pessoa ou agente escrever código.

```text
Capture → Clarify → Decide → Cut → Specify → Plan → Export → Handoff
```

O Idea termina no handoff. Execução contínua de agentes, Mission Control e
operação do Projeto Vivo pertencem a ferramentas externas.

## O que o produto entrega

- preserva a intenção original;
- separa interpretação da IA, decisão humana, hipótese e evidência;
- esclarece lacunas com profundidade proporcional ao risco;
- corta um MVP explícito;
- produz requisitos, fases e critérios verificáveis;
- exporta um pacote versionado e restaurável.

## Core e Factory

**Idea Core** prova o valor do MVP 0.1: ideia → entrevista → decisões → escopo →
spec → roadmap → exportação.

**Idea Factory** é a expansão posterior: pesquisa, validação completa,
blueprints, tarefas, contexto, guardrails e handoff assistido. F11–F20 só podem
começar depois de `G10 = PASS`.

## Modos

- **LIGHT** — utilitário ou mudança pequena, com processo proporcional;
- **STANDARD** — aplicativo ou feature normal;
- **DEEP** — sistema amplo, sensível ou multiagente.

O modo orienta profundidade; não cria capacidades que ainda não foram
implementadas.

## Invariantes principais

- `NOT_RUN` nunca significa `PASS`;
- a intenção original permanece distinta da interpretação da IA;
- somente autoridade humana apropriada fecha decisões materiais;
- regeneração não sobrescreve edição humana aceita;
- IDs, referências, dependências, locks e gates são verificáveis;
- dados podem ser lidos, editados, salvos e exportados offline;
- Android é o alvo inicial, não uma dependência do domínio futuro;
- conteúdo importado é dado, não comando executável;
- D01–D09 permanecem `LOCKED` sem decisão explícita da Product Authority.

## Como retomar

Leia primeiro:

```text
AGENTS.md → PROJECT_STATE.md → PHASE_CURRENT.md → pedido atual
```

Abra `ROADMAP.md`, `EXECUTION_PLAN.md`, `WATCHDOG.md`, `AUDIT.md` e contratos
somente quando a tarefa exigir.

## Verificação

Da raiz do repositório:

```bash
python scripts/check.py
```

Para o app Android, use o Gradle Wrapper e os testes indicados pela fase ativa.
Validação, testes, lint e build são evidências separadas; nenhum PASS é inferido
pela aparência da documentação.

## Documentos de referência

- `AGENTS.md` — contexto mínimo e regras universais;
- `PROJECT_STATE.md` — fonte única do estado operacional;
- `PHASE_CURRENT.md` — autorização e limites da fase atual;
- `ROADMAP.md` — contrato canônico de produto e evolução;
- `EXECUTION_PLAN.md` — checklist detalhado derivado do roadmap;
- `PROJECT_STANDARD.md` — modos, autoria, estados e autoridade;
- `VALIDATION_CONTRACT.md` — parse, schema e semântica;
- `CONTEXT_POLICY.md` e `AI_CONTEXT_INDEX.md` — contexto progressivo;
- `WATCHDOG.md` e `AUDIT.md` — proteção e revisão.

## Estado atual

F06.01–F06.07, F07.01–F07.06 e F08.01–F08.05 foram auditadas com PASS; `G05`,
`G06`, `G07` e `G08` foram autorizados. F09.01 está implementada e aguarda
auditoria independente. F09.02+, F10+ e a Idea Factory permanecem fora da
execução autorizada. Consulte `PROJECT_STATE.md` antes de qualquer avanço.
