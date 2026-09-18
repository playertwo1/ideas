# Idea

**Idea** é um aplicativo Android local-first para transformar uma ideia incompleta em um pacote de especificação claro, verificável e útil para implementação por pessoas ou agentes.

> Regra de ouro: reduzir ambiguidade antes de escrever código.

## Fluxo do produto

`Capture → Clarify → Decide → Cut → Specify → Plan → Export`

O Idea preserva a ideia original, ajuda a esclarecer lacunas, separa sugestões da IA de decisões humanas, corta o MVP, cria requisitos com critérios de aceite, organiza um roadmap e exporta o resultado.

O produto termina no **handoff**. Execução contínua de agentes, acompanhamento do código e Mission Control pertencem a ferramentas externas/Projeto Vivo.

## MVP 0.1

O MVP precisa provar uma coisa: alguém que receba apenas o pacote exportado deve conseguir entender:

- qual problema será resolvido;
- para quem;
- qual é o MVP;
- o que ficou fora;
- quais decisões foram tomadas pelo usuário;
- quais pontos ainda são hipótese ou pendência;
- quais requisitos devem ser atendidos;
- como verificar o resultado;
- qual sequência de implementação é sugerida.

O MVP é Android-first e local-first. Leitura, edição, salvamento e exportação não dependem da IA. Geração assistida exige conexão.

## Regras que não negociamos

- ideia original e interpretação da IA são separadas;
- IA pode sugerir, mas não pode criar decisão `LOCKED`;
- autoria e autoridade permanecem distinguíveis;
- `NOT_RUN` nunca significa `PASS`;
- IDs e referências do pacote são validados deterministicamente;
- o domínio não depende diretamente de Compose, Room ou SDK de provider;
- não construir a Idea Factory antes de o Core provar valor.

## Desenvolvimento

Papéis:

- **Product Authority:** usuário; decide produto e mudanças materiais.
- **Builder:** implementa o trabalho autorizado.
- **Auditor:** revisa mudanças relevantes de forma independente.

Não há gate humano em toda pequena fase. Cada etapa avança quando seu acceptance objetivo passa. O gate estratégico mantido é **G10**, que decide se vale expandir o Core para a futura Idea Factory.

## Documentos ativos

- `AGENTS.md` — regras mínimas para agentes;
- `PROJECT_STATE.md` — estado atual e próximo passo;
- `PRODUCT_SPEC.md` — produto, escopo e decisões atuais;
- `ROADMAP.md` — caminho enxuto até o MVP e backlog pós-MVP;
- `ARCHITECTURE.md` — arquitetura essencial e baseline Android;
- `VALIDATION_CONTRACT.md` — regras determinísticas do formato;
- `WATCHDOG.md` — segurança/anti-drift para tarefas de risco;
- `AUDIT.md` — método de auditoria independente.

Contratos executáveis ficam em `schemas/`, exemplos em `fixtures/` e validações em `scripts/`.

Para verificar os contratos existentes:

```bash
python scripts/check.py
```

O DOCX original permanece apenas como pesquisa histórica. O Git preserva versões anteriores dos documentos removidos durante a simplificação.
