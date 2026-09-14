# VALIDATION_CONTRACT — M0

Este documento materializa a parte semântica de F00.03 que não pode ser expressa de forma confiável apenas com JSON Schema.

`ROADMAP.md` continua canônico. JSON Schema valida forma/tipos/padrões; este contrato valida integridade entre entidades.

## Pipeline de validação

A ordem mínima é:

1. parse JSON;
2. validar o documento contra o schema 2020-12 correspondente;
3. executar validações semânticas determinísticas;
4. somente depois permitir que o documento seja tratado como estruturalmente válido.

Falha em qualquer etapa produz `FAIL`; ausência de execução produz `NOT_RUN`.

## Regras semânticas mínimas do `project.json`

### VAL-001 — IDs únicos

Todos os IDs materiais do projeto devem ser únicos dentro do namespace global do documento.

Coleções inicialmente cobertas em 0.1:

- `decisions[].id`;
- `requirements[].id`;
- `gates[].id`.

Dois objetos diferentes não podem compartilhar o mesmo ID, ainda que o restante do conteúdo seja diferente.

### VAL-002 — referências devem existir

Toda referência interna obrigatória deve apontar para entidade existente no mesmo snapshot.

No schema 0.1 atual:

- cada valor de `requirements[].decisionRefs[]` deve existir em `decisions[].id`.

Quando novas relações forem adicionadas ao schema, entram nesta regra antes de serem consideradas prontas.

### VAL-003 — namespace coerente

O prefixo do ID precisa ser compatível com a entidade:

- decisão: `DEC-*`;
- requisito funcional/não funcional: `REQ-*` ou `NFR-*`;
- gate: `GATE-*`.

O JSON Schema faz a primeira barreira; o validador semântico pode emitir erro mais explicável.

### VAL-004 — integridade não é inventada

Se uma referência necessária está ausente, o validador não cria automaticamente a entidade faltante, não remove a referência e não converte o problema em warning silencioso. O documento permanece inválido até correção explícita.

## Fixtures obrigatórias de F00.03

Casos positivos:

- `fixtures/light-simple.project.json`;
- `fixtures/standard-medium.project.json`;
- `fixtures/deep.project.json`.

Casos negativos:

- `fixtures/invalid-duplicate-id.project.json` deve falhar por `VAL-001`;
- `fixtures/invalid-missing-ref.project.json` deve falhar por `VAL-002`.

## Critério de aceite de F00.03

F00.03 só pode ser marcada como verificada quando:

- os três schemas existem;
- pelo menos uma fixture válida passa no schema e na camada semântica;
- o caso de ID duplicado falha;
- o caso de referência ausente falha;
- a evidência registra separadamente o que foi validado por schema e o que foi validado semanticamente.

## Implementação futura

A implementação Android deve portar estas regras para uma camada de domínio testável, sem dependência de UI, Room ou provider de IA. O comportamento do validador é parte do contrato portátil do Core.