# G00 — Audit Packet

Este arquivo prepara a auditoria independente de saída de F00. Ele não contém aprovação do gate.

## Objetivo

Determinar se F00 — Padrão de projeto e congelamento do MVP — está suficientemente completa, coerente e verificável para permitir que a Product Authority decida sobre `G00`.

O Auditor deve tentar falsificar a prontidão, não confirmar o Builder por padrão.

## Bootstrap da auditoria

Comece somente com:

1. `AGENTS.md`
2. `PROJECT_STATE.md`
3. este `G00_AUDIT_PACKET.md`
4. `AUDIT.md`

Use `AI_CONTEXT_INDEX.md`/`context-manifest.json` para carregar artefatos adicionais conforme o finding investigado.

Não leia ROADMAP completo nem todos os artefatos F00 antecipadamente.

## Rotas de investigação

### Produto, escopo e fronteira Idea/Vivo
Carregar:
- `M0_PRODUCT_CONTRACT.md`
- `M0_DECISION_BASELINE.md` se houver questão sobre D01–D09
- seção específica do `ROADMAP.md` somente se a redação materializada não resolver a dúvida

Verificar:
- problema explícito;
- público D01 preservado;
- hipótese de valor falsificável;
- Capture → Clarify → Decide → Cut → Specify → Plan → Export coerente;
- non-goals impedem expansão prematura;
- Idea termina no handoff.

### Standard, autoridade e estados
Carregar:
- `PROJECT_STANDARD.md`
- `M0_GOVERNANCE.md` quando necessário

Verificar:
- LIGHT/STANDARD/DEEP proporcionais;
- autoria diferente de autoridade;
- IA não promove conteúdo para LOCKED;
- `NOT_RUN != PASS`;
- `NOT_APPLICABLE` exige rationale não vazio;
- ausência/conflito não vira fato inventado;
- Core/Factory e papéis permanecem separados.

### Schemas e integridade
Carregar:
- `VALIDATION_CONTRACT.md`
- schema afetado em `schemas/`
- fixture(s) diretamente relacionadas em `fixtures/`
- `scripts/validate_contract.py`

Só ampliar para outras fixtures se o teste/finding exigir.

Verificar pelo menos:
- schemas válidos em Draft 2020-12;
- fixtures positivas válidas;
- pipeline separa parse, estrutura, semântica e expectativa;
- duplicidades materiais falham por `VAL-001`;
- referências ausentes/autorreferência falham por `VAL-002`;
- LOCKED sem autoridade humana falha por `VAL-003`;
- namespace incompatível usa `VAL-004`;
- `NOT_APPLICABLE` sem rationale falha por `VAL-005`;
- nenhuma falsa promessa é atribuída ao JSON Schema.

### Artefatos, modos e fixtures
Carregar conforme necessidade:
- `ARTIFACT_CATALOG.md`
- `fixtures/light-simple.project.json`
- `fixtures/standard-medium.project.json`
- `fixtures/deep.project.json`

Verificar:
- matriz não exige capacidade impossível no 0.1;
- existem casos LIGHT/STANDARD/DEEP;
- DEEP 0.1 não finge implementar Factory;
- lacunas conhecidas continuam explícitas.

### Decisões, freeze e portabilidade
Carregar:
- `M0_DECISION_BASELINE.md`
- `M0_GOVERNANCE.md`
- `PROJECT_STANDARD.md` se necessário

Verificar:
- D01–D09 não foram materialmente alteradas;
- corte 0.1 é claro;
- nenhuma decisão nova foi marcada indevidamente como humana/LOCKED;
- F11–F20 continuam bloqueadas com `G10 != PASS`;
- Android-first não contaminou o Core;
- nenhum segundo target/framework multiplataforma foi criado prematuramente.

### Evidência declarada
Carregar:
- `M0_VALIDATION_EVIDENCE.md`

Use a evidência como alegação a verificar, não como prova automática. Repita checks relevantes quando possível.

### Eficiência de contexto do próprio repositório
Carregar:
- `M0_CONTEXT_EFFICIENCY.md`
- `CONTEXT_POLICY.md`
- `AI_CONTEXT_INDEX.md`
- `context-manifest.json`

Verificar:
- bootstrap mínimo não omite autoridade crítica;
- WATCHDOG/AUDIT entram por gatilho/tarefa sem perder segurança;
- status dinâmico tem uma fonte principal;
- ROADMAP completo não é leitura padrão;
- overflow obrigatório nunca autoriza truncamento silencioso;
- esta baseline não se apresenta falsamente como F15 implementada.

## Procedimento reproduzível

Quando aplicável, execute:

```sh
python scripts/validate_contract.py
```

Execute testes do validador se disponíveis. Limite logs inicialmente e amplie apenas em caso de falha que exija investigação.

## Condições de bloqueio de G00

G00 não deve receber recomendação de PASS se houver, entre outros:

- contradição material com D01–D09;
- ambiguidade capaz de iniciar Android/Factory antes da hora;
- fixture positiva inválida;
- caso negativo previsto não detectado;
- requisito impossível tornado obrigatório no 0.1;
- autoridade humana/IA indistinguível em decisão material;
- dependência prematura de plataforma no Core;
- política de contexto que possa omitir silenciosamente regra crítica;
- finding CRITICAL/HIGH não resolvido.

## Saída esperada

Terminar exatamente com:

`AUDIT RESULT: PASS`

ou

`AUDIT RESULT: FAIL`

Se FAIL, informar severidade, arquivo/regra, problema, impacto e condição objetiva de resolução.

Mesmo com `AUDIT RESULT: PASS`, `G00` continua não registrado até a Product Authority avaliar as evidências e declarar explicitamente `G00 = PASS`.

O Auditor não inicia F01 e o Builder não registra o próprio gate.
