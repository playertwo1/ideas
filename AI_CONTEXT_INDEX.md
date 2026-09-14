# AI_CONTEXT_INDEX — Roteador de contexto

> Índice operacional para agentes. Use este arquivo para decidir **o que ler**, não como nova fonte de verdade. Em conflito, valem as autoridades já definidas pelo projeto.

## Bootstrap padrão

Carregue sempre:
1. `AGENTS.md`
2. `PROJECT_STATE.md`
3. pedido vigente da Product Authority

Depois escolha apenas a rota correspondente à tarefa.

## Rotas de contexto

### `STATE_OR_RESUME`
Use quando a pergunta for: onde estamos, qual próximo passo, qual gate ou bloqueio?

**REQUIRED**
- `PROJECT_STATE.md`

**CONDITIONAL**
- `PHASE_CURRENT.md` — se precisar de autorização/limites da fase
- `EXECUTION_PLAN.md` — se precisar da sequência/checklist
- `ROADMAP.md` — somente a seção específica quando existir dúvida canônica

### `DOC_ONLY`
Mudança textual/local sem alterar regra canônica.

**REQUIRED**
- arquivo alvo
- `PROJECT_STATE.md`

**CONDITIONAL**
- `PHASE_CURRENT.md` — se o texto depender da fase ativa
- documento canônico referenciado pelo arquivo alvo

**Não carregar por padrão**
- ROADMAP completo
- auditorias antigas
- DOCX histórico

### `G00_AUDIT`
Auditoria de saída da F00.

**REQUIRED**
- `G00_AUDIT_PACKET.md`
- `PROJECT_STATE.md`
- `AUDIT.md`

**CONDITIONAL por finding**
- produto/escopo → `M0_PRODUCT_CONTRACT.md`
- autoridade/LOCKED → `M0_DECISION_BASELINE.md`, `M0_GOVERNANCE.md`
- IDs/estados/regras → `PROJECT_STANDARD.md`
- schema/semântica → `VALIDATION_CONTRACT.md`, schema/fixture específica, `scripts/validate_contract.py`
- evidência → `M0_VALIDATION_EVIDENCE.md`
- regra canônica → seção específica de `ROADMAP.md`

Não abra todos os artefatos F00 de uma vez sem necessidade.

### `SCHEMA_VALIDATION`
Alteração ou auditoria de schema/validador.

**REQUIRED**
- schema afetado em `schemas/`
- `VALIDATION_CONTRACT.md`
- `scripts/validate_contract.py`
- fixture(s) diretamente relacionadas

**CONDITIONAL**
- `PROJECT_STANDARD.md` — IDs, autoria, estados, gates
- `M0_VALIDATION_EVIDENCE.md` — para comparar expectativa já registrada
- `ROADMAP.md` — somente quando regra canônica não estiver materializada acima

### `PRODUCT_CONTRACT`
Mudança em problema, público, hipótese de valor, escopo ou non-goals.

**REQUIRED**
- `M0_PRODUCT_CONTRACT.md`
- `M0_DECISION_BASELINE.md`

**CONDITIONAL**
- se tocar D01–D09 → seção de decisões do `ROADMAP.md`
- se tocar Core/Factory/G10 → `M0_GOVERNANCE.md`
- se tocar artefatos por modo → `ARTIFACT_CATALOG.md`

Mudança material exige Product Authority.

### `GOVERNANCE_OR_GATE`
Mudança em autorização, papéis, gate ou avanço de fase.

**REQUIRED**
- `PROJECT_STATE.md`
- `PHASE_CURRENT.md`
- `M0_GOVERNANCE.md`

**CONDITIONAL**
- `AUDIT.md` — se envolver resultado de auditoria
- `EXECUTION_PLAN.md` — sequência/checklist
- seção de gate/autoridade do `ROADMAP.md` — se houver dúvida canônica

### `CONTEXT_EFFICIENCY`
Mudanças na forma como agentes recebem contexto ou em F04.07/F15.

**REQUIRED**
- `CONTEXT_POLICY.md`
- `AI_CONTEXT_INDEX.md`
- `context-manifest.json`

**CONDITIONAL**
- `PROJECT_STANDARD.md` — autoridade/estado/rationale
- seção F04.07 do `ROADMAP.md` — custo/tokens e envio mínimo
- seção F15 do `ROADMAP.md` — Context Compiler
- F16/F17 — guardrails/revisão independente quando afetados

### `REFERENCE_RESEARCH`
Pesquisa ou proveniência de padrões externos.

**REQUIRED**
- `REFERENCE_MATRIX.md`

**CONDITIONAL**
- fonte externa específica
- seção do produto que adotará o padrão

O README e ROADMAP não devem duplicar detalhes de referência quando um link/ID resolver.

### `ANDROID_FUTURE`
Somente quando F01/G00 autorizarem trabalho correspondente.

Antes de Android existir, esta rota NÃO autoriza implementação.

**REQUIRED quando autorizada**
- `PROJECT_STATE.md`
- `PHASE_CURRENT.md`
- artefatos F01/F02 correspondentes
- módulo/arquivo diretamente afetado

**CONDITIONAL**
- arquitetura/ADR relevante
- testes do módulo
- Watchdog se houver risco alto

## Gatilhos para `WATCHDOG.md`

Carregue `WATCHDOG.md` quando houver:
- exclusão de arquivos/dados;
- migration ou persistência;
- autenticação/permissão/segurança;
- credenciais/secrets;
- dependência nova relevante;
- mudança arquitetural;
- force push/rewrite Git;
- operação externa/destrutiva;
- três falhas semelhantes ou loop de erro.

Para edição documental simples, ele não precisa estar no bootstrap.

## Gatilhos para `AUDIT.md`

Carregue `AUDIT.md` quando:
- atuar no papel Auditor;
- fechar gate;
- revisar mudança relevante para PASS/FAIL;
- Product Authority pedir auditoria independente.

## Regra de escalonamento

Se a rota não fornecer contexto suficiente:
1. identifique a lacuna concreta;
2. carregue apenas o item `CONDITIONAL` que resolve essa lacuna;
3. repita somente se ainda faltar informação material;
4. abra referência profunda/ROADMAP amplo por último.

## Regra anti-repetição

Uma informação dinâmica deve ter uma casa canônica:
- estado atual → `PROJECT_STATE.md`
- autorização da fase → `PHASE_CURRENT.md`
- checklist completo → `EXECUTION_PLAN.md`
- regra canônica/decisão → `ROADMAP.md`
- evidência → arquivo de evidência correspondente
- referências externas → `REFERENCE_MATRIX.md`
- eficiência de contexto → `CONTEXT_POLICY.md`

Outros arquivos devem apontar para a fonte em vez de copiar o conteúdo.