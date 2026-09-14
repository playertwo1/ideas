# VALIDATION_CONTRACT — M0

Este documento materializa a validação semântica de F00.03 que não pode ser
expressa apenas com JSON Schema. `ROADMAP.md` continua canônico.

## Pipeline obrigatório

Cada documento é processado nesta ordem, sem misturar resultados:

1. **Parse:** ler o arquivo e decodificar JSON;
2. **Estrutural:** validar contra o schema Draft 2020-12 correspondente;
3. **Semântico:** executar regras determinísticas somente se o parse e a
   validação estrutural passarem;
4. **Expectativa:** para fixtures negativas, comparar exatamente o conjunto de
   códigos semânticos esperado. Uma substring coincidente não é suficiente.

Falha em qualquer etapa produz `FAIL`; ausência de execução produz `NOT_RUN`.

## Regras semânticas mínimas do `project.json`

### VAL-001 — IDs materiais únicos

Todos os IDs materiais são únicos no namespace global do snapshot. As coleções
F00 cobertas são:

- `decisions[].id` (`DEC-*`);
- `requirements[].id` (`REQ-*` e `NFR-*`);
- `roadmap[].id` (`PHASE-*` e `TASK-*`);
- `gaps[].id` (`GAP-*`);
- `gates[].id` (`GATE-*`);
- `references[].id` (`REF-*`).

A regra detecta repetição dentro de uma coleção e colisão entre coleções,
mesmo que os objetos tenham conteúdo diferente. Entidades exclusivas da
Idea Factory não fazem parte desta lista.

### VAL-002 — referências internas existentes

Toda referência interna coberta pelo schema deve apontar para entidade
existente no mesmo snapshot:

- `requirements[].decisionRefs[]` deve existir em `decisions[].id`;
- `roadmap[].dependsOn[]` deve existir em `roadmap[].id`;
- um item do roadmap não pode depender de si mesmo.

Detecção completa de ciclos do roadmap permanece fora deste contrato F00.

### VAL-003 — decisão LOCKED exige autoridade humana explícita

Uma decisão `LOCKED` somente é válida quando `author=USER`, `authority=USER`
e `lockAction=HUMAN_EXPLICIT`. IA ou autoridade não humana não pode fechar a
decisão.

### VAL-004 — namespace incompatível

O prefixo do ID deve ser compatível com sua coleção, conforme a lista de
`VAL-001`. O schema fornece a primeira barreira; este código mantém a regra
explícita na camada semântica para valores avaliados fora do parser de schema.

### VAL-005 — NOT_APPLICABLE exige rationale

`NOT_APPLICABLE` exige rationale presente, textual e não vazio após remover
espaços. A regra vale para `gates[].status`, `roadmap[].gateStatus` e para
`manifest.gateResults[].status`. O schema exige a presença do campo; a camada
semântica exige conteúdo não vazio.

`NOT_RUN` continua sendo um estado distinto: não executado nunca equivale a
`PASS`.

### Integridade não é inventada

Se uma referência necessária está ausente, o validador não cria entidade,
remove referência ou transforma o problema em warning silencioso.

## Fixtures negativas

Cada fixture negativa deve ser JSON parseável, passar estruturalmente, falhar
semanticamente e apresentar exatamente os códigos listados:

- `invalid-duplicate-id.project.json` → `VAL-001`;
- `invalid-missing-ref.project.json` → `VAL-002`;
- `invalid-ai-locked.project.json` → `VAL-003`;
- `invalid-duplicate-gate-id.project.json` → `VAL-001`;
- `invalid-duplicate-roadmap-id.project.json` → `VAL-001`;
- `invalid-missing-roadmap-ref.project.json` → `VAL-002`;
- `invalid-not-applicable-rationale.project.json` → `VAL-005`.

O script também verifica que remover o defeito esperado ou introduzir um erro
adicional faz a expectativa exata falhar com código de saída diferente de zero.

## Critério de aceite de F00.03

F00.03 só pode ser considerada verificada quando os três schemas existem,
todos os exemplos válidos passam as três primeiras etapas, cada fixture
negativa passa estruturalmente e falha semanticamente com o conjunto exato
esperado, e os testes adversariais passam.

## Implementação futura

A implementação Android deve portar estas regras para uma camada de domínio
testável, sem dependência de UI, Room ou provider de IA. O comportamento do
validador é parte do contrato portátil do Core.
