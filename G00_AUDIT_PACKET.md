# G00 — Audit Packet

Este arquivo prepara a auditoria independente de saída de F00. Ele não contém aprovação do gate.

## Objetivo da auditoria

Determinar se F00 — Padrão de projeto e congelamento do MVP — está suficientemente completa, coerente e verificável para permitir que a Product Authority decida sobre `G00`.

O Auditor deve tentar falsificar a prontidão, não apenas confirmar o trabalho do Builder.

## Fontes canônicas e operacionais

Ler na ordem mínima necessária:

1. `PROJECT_STATE.md`
2. `PHASE_CURRENT.md`
3. `WATCHDOG.md`
4. `ROADMAP.md` — somente seções necessárias, especialmente F00, protocolo, D01–D09 e G10
5. `AUDIT.md`

Artefatos produzidos por F00:

- `M0_PRODUCT_CONTRACT.md`
- `PROJECT_STANDARD.md`
- `schemas/project.schema.json`
- `schemas/manifest.schema.json`
- `schemas/ai-response.schema.json`
- `VALIDATION_CONTRACT.md`
- `ARTIFACT_CATALOG.md`
- `M0_DECISION_BASELINE.md`
- `M0_GOVERNANCE.md`
- `M0_VALIDATION_EVIDENCE.md`
- `fixtures/light-simple.project.json`
- `fixtures/standard-medium.project.json`
- `fixtures/deep.project.json`
- `fixtures/invalid-duplicate-id.project.json`
- `fixtures/invalid-missing-ref.project.json`
- `fixtures/invalid-ai-locked.project.json`
- `fixtures/invalid-duplicate-gate-id.project.json`
- `fixtures/invalid-duplicate-roadmap-id.project.json`
- `fixtures/invalid-missing-roadmap-ref.project.json`
- `fixtures/invalid-not-applicable-rationale.project.json`
- `fixtures/valid.manifest.json`
- `fixtures/valid.ai-response.json`

## O que o Auditor deve verificar

### F00.01 — Contrato do produto

- problema explícito e coerente com o roadmap;
- público D01 preservado;
- hipótese de valor falsificável;
- exemplo ponta a ponta cobre Capture → Clarify → Decide → Cut → Specify → Plan → Export;
- non-goals impedem expansão prematura;
- fronteira Idea/Projeto Vivo continua clara.

### F00.02 — Standard

- LIGHT/STANDARD/DEEP proporcionais;
- autoria e autoridade não confundidas;
- estados não promovem conteúdo de IA a LOCKED;
- `NOT_RUN != PASS`;
- `NOT_APPLICABLE` exige rationale não vazio e `N_A` não é valor executável;
- ausência/conflito não vira fato inventado.

### F00.03 — Schemas e integridade

- schemas são válidos em Draft 2020-12;
- fixture válida passa;
- o pipeline separa parse, estrutura, semântica e comparação exata;
- duplicidades em decisions, requirements, roadmap, gaps, gates e references falham por `VAL-001`;
- referências ausentes e autorreferência de roadmap falham por `VAL-002`;
- LOCKED sem autoridade humana explícita falha por `VAL-003`;
- namespace incompatível usa `VAL-004`, sem conflitar com LOCKED;
- `NOT_APPLICABLE` sem rationale não vazio falha por `VAL-005`;
- diferença entre validação estrutural e semântica está explícita, sem falsa promessa do JSON Schema.

### F00.04–F00.05 — Artefatos e fixtures

- matriz não exige capacidade ainda impossível no MVP 0.1;
- existem casos LIGHT, STANDARD e DEEP;
- DEEP 0.1 não finge implementar Factory;
- faltas conhecidas permanecem visíveis.

### F00.06 — Freeze e decisões

- D01–D09 refletem o `ROADMAP.md` sem alteração material;
- corte 0.1 permite identificar claramente o que entra e o que fica fora;
- nenhuma nova decisão foi indevidamente marcada como humana/LOCKED.

### F00.07–F00.08 — Governança e portabilidade

- Core e Factory são distinguíveis;
- F11–F20 permanecem bloqueadas enquanto `G10 != PASS`;
- Builder, Auditor e Product Authority têm papéis separados;
- Android-first não contaminou regras centrais com dependência obrigatória de Android;
- não foi criado segundo target nem framework multiplataforma prematuro.

## Evidência declarada pelo trabalho de preparação

`M0_VALIDATION_EVIDENCE.md` registra:

- sintaxe dos três schemas: PASS;
- LIGHT/STANDARD/DEEP: schema PASS + semantic PASS;
- manifest válido: PASS;
- resposta IA válida: PASS;
- todas as fixtures negativas: parse PASS, estrutural PASS e conjunto exato de códigos semânticos conforme `VALIDATION_CONTRACT.md`;
- testes adversariais cobrem gate duplicado, dependência inexistente/autorreferência, `NOT_APPLICABLE`, AI/non-human + LOCKED, `N_A` e falsos PASS.

O Auditor pode e deve repetir checks relevantes quando possível.

## Condições de bloqueio de G00

G00 não deve receber recomendação de PASS se houver, entre outros:

- contradição material com D01–D09;
- ambiguidade capaz de fazer Builder iniciar Android ou Factory antes da hora;
- fixture positiva estruturalmente inválida;
- caso negativo previsto não detectado pelo contrato de validação;
- requisito impossível tornado obrigatório no 0.1;
- autoridade humana/IA indistinguível em decisão material;
- segundo target ou dependência prematura de plataforma no Core;
- finding CRITICAL/HIGH não resolvido.

## Saída esperada do Codex

O resultado deve seguir `AUDIT.md` e terminar exatamente com um dos estados:

`AUDIT RESULT: PASS`

ou

`AUDIT RESULT: FAIL`

Se houver findings, classificar severidade e citar arquivo/regra afetados e condição objetiva de resolução.

## Regra final

Mesmo com `AUDIT RESULT: PASS`, `G00` continua `NOT_RUN`/não registrado até a Product Authority avaliar a evidência e registrar explicitamente `G00 = PASS`.

O Auditor não inicia F01 e o Builder não registra o próprio gate.
