# IDEA — PHASE CURRENT

> Contrato operacional da fase ativa. Derivado do `ROADMAP.md` v0.6. Em conflito, o `ROADMAP.md` vence.

## Fase ativa

**F00 — Padrão de projeto e congelamento do MVP**

**Objetivo:** fechar o contrato do produto e da fundação antes de iniciar implementação Android.

**Builder:** Antigravity  
**Auditor:** Codex  
**Product Authority:** usuário

## Resultado esperado

Contrato versionado que permita identificar objetivamente o que falta em um pacote, com Core/Factory, autoridade, schemas, gates, fixtures, decisões fundamentais e portabilidade futura definidos.

## Trabalho autorizado

### Estado das decisões para F00.01

- `LOCKED`: D01–D09, incluindo público inicial e distribuição.
- A formalizar/verificar: problema, hipótese de valor, exemplo ponta a ponta e non-goals.
- A decidir: somente material novo descoberto durante a formalização; não repetir perguntas já resolvidas por D01–D09.


### [ ] F00.01
**Fazer:** Formalizar problema, público D01 já LOCKED, hipótese de valor e fronteira Idea/Vivo  
**Concluir quando:** Um exemplo real percorre o fluxo; non-goals escritos; D01–D09 não foram reabertos

### [ ] F00.02
**Fazer:** Produzir `PROJECT_STANDARD.md`: modos, IDs, autoria, estados e autoridade  
**Concluir quando:** LIGHT não exige capacidades DEEP; casos ambíguos têm regra

### [ ] F00.03
**Fazer:** Definir `project.schema.json`, `manifest.schema.json` e schemas de resposta IA  
**Concluir quando:** Exemplo válido passa; ID duplicado e referência ausente falham

### [ ] F00.04
**Fazer:** Fixar artefatos por versão/modo e catálogo de gates  
**Concluir quando:** Matriz sem artefato obrigatório impossível na versão

### [ ] F00.05
**Fazer:** Preparar três fixtures: utilitário simples, app médio e projeto sensível  
**Concluir quando:** Faltas conhecidas são detectadas, não preenchidas como fatos

### [ ] F00.06
**Fazer:** Materializar D01–D09 LOCKED nos ADRs/contratos aplicáveis e congelar corte 0.1  
**Concluir quando:** Usuário identifica exatamente o que entra e o que fica fora

### [ ] F00.07
**Fazer:** Formalizar Core/Factory, G10 Go/No-Go, Progressive Commitment, rationale e protocolo Builder/Auditor  
**Concluir quando:** Contratos impedem F11+ sem G10 PASS e distinguem autoridade de produto, execução e auditoria

### [ ] F00.08
**Fazer:** Formalizar D09 e Platform Portability Principle sem adicionar um segundo target ao MVP  
**Concluir quando:** PROJECT_STANDARD separa Core de adapters e proíbe abstração multiplataforma prematura

## Pode fazer sem nova autorização

- Criar/editar documentação, schemas, fixtures e ADRs necessários exclusivamente à F00.
- Corrigir inconsistências objetivas encontradas dentro da F00.
- Executar validações locais e preparar evidências para auditoria.
- Refinar estrutura/clareza sem alterar decisões de produto já aprovadas.

## Não está autorizado nesta fase

- Iniciar implementação Android, Compose, Room ou integração real com provider.
- Avançar para F01 antes de G00 = PASS.
- Implementar capacidades F11–F20 / Idea Factory.
- Alterar materialmente o escopo do MVP 0.1 sem Product Authority.
- Transformar possibilidade futura Desktop/Web em target atual ou adicionar abstrações multiplataforma prematuras.
- Executar ações externas/destrutivas não necessárias à F00.

## Decisões fundamentais

- D01–D09 estão **APROVADAS/LOCKED pela Product Authority em 13/09/2026** conforme catálogo do `ROADMAP.md`.
- O Builder deve implementá-las como restrições vigentes; não deve reabri-las por preferência técnica.
- Qualquer nova decisão descoberta deve ser apresentada como proposta; o agente não pode promovê-la sozinho a decisão humana LOCKED.
- Alterar D01–D09 exige pedido explícito da Product Authority e análise de impacto.

## Gate G00

**Critério canônico:** três fixtures auditáveis, contrato versionado e bloqueadores de fundação resolvidos.

Checklist de saída:
- [ ] F00.01–F00.08 concluídas e verificadas
- [ ] `PROJECT_STANDARD.md` coerente com o roadmap
- [ ] Schemas validam fixture correta e rejeitam casos inválidos previstos
- [ ] Catálogo de artefatos/gates sem impossibilidades
- [ ] D01–D09 registradas no estado correto
- [ ] Core/Factory e G10 formalizados
- [ ] Portabilidade preservada sem novo target
- [ ] Codex realizou auditoria proporcional
- [ ] Findings bloqueantes resolvidos
- [ ] Auditor emitiu `AUDIT RESULT: PASS`
- [ ] Product Authority registrou explicitamente `G00 = PASS`

## Retorno seguro

Se G00 falhar, permanecer em F00 e revisar contratos/fixtures/decisões. Não há necessidade de migrar dados de usuário porque a implementação do produto ainda não deve ter começado.

## Ao terminar

1. Atualizar `PROJECT_STATE.md`.
2. Marcar os itens correspondentes em `EXECUTION_PLAN.md`.
3. Entregar evidências ao Codex para auditoria.
4. Builder não registra o próprio gate. Somente após `AUDIT RESULT: PASS`, checks objetivos satisfeitos e Product Authority registrar explicitamente `G00 = PASS`, gerar/substituir `PHASE_CURRENT.md` para F01.
