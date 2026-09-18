# AGENTS.md — Idea

Contrato mínimo para qualquer agente que trabalhe neste repositório.

## Comece aqui

Leia somente:

1. `PROJECT_STATE.md`;
2. o pedido atual;
3. o arquivo diretamente relacionado à tarefa.

Consulte `PRODUCT_SPEC.md`, `ROADMAP.md`, `ARCHITECTURE.md` ou `VALIDATION_CONTRACT.md` apenas quando a tarefa precisar deles. Não leia o repositório inteiro por padrão.

## Autoridade

Ordem prática:

**segurança → pedido vigente da Product Authority → PRODUCT_SPEC → ROADMAP → PROJECT_STATE → julgamento técnico.**

O estado atual nunca redefine silenciosamente o produto.

## Papéis

- **Product Authority:** decide produto, escopo e mudanças materiais.
- **Builder:** executa o trabalho já autorizado e produz evidência.
- **Auditor:** tenta encontrar falhas em mudanças relevantes; não aprova por confiança.

## Regras essenciais

- Faça a menor mudança suficiente.
- Preserve trabalho existente e contratos executáveis.
- Não implemente capacidade futura só porque está mencionada no backlog.
- Não transforme sugestão de IA em decisão humana.
- IA não define IDs definitivos, `LOCKED`, autorização ou gate `PASS`.
- `NOT_RUN != PASS`.
- Não esconda falhas, remova validações para “passar”, nem use mocks como produção.
- Não crie backend, sync, framework multiplataforma ou abstração sem necessidade concreta.

## Quando ampliar contexto

Abra `WATCHDOG.md` para exclusão de dados/arquivos, persistência/migration, credenciais, segurança, dependência material, mudança arquitetural, operação destrutiva ou loop de falhas.

Abra `AUDIT.md` quando atuar como Auditor ou revisar uma mudança relevante.

## Conclusão

Antes de concluir:

1. valide o acceptance diretamente afetado;
2. execute checks proporcionais ao risco;
3. revise o diff;
4. atualize `PROJECT_STATE.md` somente se o estado realmente mudou.

**LOCATE → UNDERSTAND → CHANGE → VERIFY.**
