# IDEA — PHASE CURRENT

> Contrato operacional da fase ativa. Derivado do `ROADMAP.md` v0.6. Em conflito, o `ROADMAP.md` vence.

## Fase ativa

**F00 — Padrão de projeto e congelamento do MVP**

**Subestado:** findings do G00 corrigidos dentro de F00; validação reproduzível executada; G00 continua aguardando nova auditoria independente e registro final da Product Authority.

**Objetivo:** fechar o contrato do produto e da fundação antes de iniciar implementação Android.

**Builder:** Antigravity  
**Auditor:** Codex  
**Product Authority:** usuário

## Resultado esperado

Contrato versionado que permita identificar objetivamente o que falta em um pacote, com Core/Factory, autoridade, schemas, gates, fixtures, decisões fundamentais e portabilidade futura definidos.

## Trabalho autorizado

### Estado das decisões

- `LOCKED`: D01–D09, incluindo público inicial e distribuição.
- Nenhuma D01–D09 foi reaberta durante F00.
- Nova decisão humana material: nenhuma identificada.
- O baseline materializado está em `M0_DECISION_BASELINE.md`; `ROADMAP.md` permanece canônico.

### [x] F00.01
**Fazer:** Formalizar problema, público D01 já LOCKED, hipótese de valor e fronteira Idea/Vivo  
**Artefato:** `M0_PRODUCT_CONTRACT.md`  
**Verificado:** exemplo ponta a ponta e non-goals explícitos; D01–D09 preservadas

### [x] F00.02
**Fazer:** Produzir `PROJECT_STANDARD.md`: modos, IDs, autoria, estados e autoridade  
**Verificado:** LIGHT não exige capacidades DEEP; casos ambíguos têm regra

### [x] F00.03
**Fazer:** Definir `project.schema.json`, `manifest.schema.json` e schemas de resposta IA  
**Artefatos:** `schemas/` + `VALIDATION_CONTRACT.md` + `M0_VALIDATION_EVIDENCE.md`  
**Verificado:** fixtures válidas passam; ID duplicado e referência ausente falham na camada semântica determinística

### [x] F00.04
**Fazer:** Fixar artefatos por versão/modo e catálogo de gates  
**Artefato:** `ARTIFACT_CATALOG.md`  
**Verificado:** matriz não obriga capacidade Factory impossível no MVP 0.1

### [x] F00.05
**Fazer:** Preparar três fixtures: utilitário simples, app médio e projeto sensível  
**Artefatos:** `fixtures/light-simple.project.json`, `fixtures/standard-medium.project.json`, `fixtures/deep.project.json`  
**Verificado:** faltas conhecidas permanecem explícitas; DEEP não inventa capacidades futuras

### [x] F00.06
**Fazer:** Materializar D01–D09 LOCKED nos ADRs/contratos aplicáveis e congelar corte 0.1  
**Artefato:** `M0_DECISION_BASELINE.md`  
**Verificado:** entradas e exclusões do MVP 0.1 estão explícitas sem alterar D01–D09

### [x] F00.07
**Fazer:** Formalizar Core/Factory, G10 Go/No-Go, Progressive Commitment, rationale e protocolo Builder/Auditor  
**Artefatos:** `M0_GOVERNANCE.md` + `PROJECT_STANDARD.md`  
**Verificado:** F11+ permanece bloqueada sem G10 PASS; autoridade de produto, execução e auditoria estão separadas

### [x] F00.08
**Fazer:** Formalizar D09 e Platform Portability Principle sem adicionar um segundo target ao MVP  
**Artefatos:** `M0_GOVERNANCE.md` + `PROJECT_STANDARD.md`  
**Verificado:** Core conceitualmente independente de Android; nenhum segundo target/framework multiplataforma foi adicionado

## Pode fazer sem nova autorização

- Corrigir finding objetivo da auditoria que esteja dentro do contrato F00.
- Refinar documentação, schemas e fixtures de F00 sem alterar decisões LOCKED.
- Reexecutar validações afetadas e atualizar evidências.
- Preparar material de auditoria e sincronizar estado/checklists operacionais.

A autorização acima **não** permite alterar semântica canônica do `ROADMAP.md` por preferência editorial. Mudança de produto, decisão LOCKED, escopo ou regra canônica exige a autoridade prevista na hierarquia documental.

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

**Estado:** `NOT_RUN` — aguardando Codex/Auditor.

**Critério canônico:** três fixtures auditáveis, contrato versionado e bloqueadores de fundação resolvidos.

Checklist de saída:
- [x] F00.01–F00.08 concluídas e verificadas pelo trabalho de preparação
- [x] `PROJECT_STANDARD.md` coerente com o roadmap
- [x] Schemas + validação semântica aceitam fixtures corretas e rejeitam casos inválidos previstos
- [x] Catálogo de artefatos/gates sem capacidade impossível obrigatória no 0.1
- [x] D01–D09 materializadas no estado LOCKED correto
- [x] Core/Factory e G10 formalizados
- [x] Portabilidade preservada sem novo target
- [ ] Codex realizou auditoria proporcional
- [x] Findings bloqueantes da auditoria resolvidos pelo Builder; aguardando confirmação independente
- [ ] Auditor emitiu `AUDIT RESULT: PASS`
- [ ] Product Authority registrou explicitamente `G00 = PASS`

## Retorno seguro

Se G00 falhar, permanecer em F00 e revisar apenas contratos/fixtures/decisões afetados. Não há necessidade de migrar dados de usuário porque a implementação do produto ainda não começou.

## Próxima ação

1. Entregar `G00_AUDIT_PACKET.md` e os artefatos referenciados ao Codex.
2. Codex executar auditoria proporcional conforme `AUDIT.md`.
3. Findings corrigíveis dentro de F00 retornam ao Builder sem nova decisão humana.
4. Somente após `AUDIT RESULT: PASS`, checks objetivos satisfeitos e Product Authority registrar explicitamente `G00 = PASS`, substituir `PHASE_CURRENT.md` pela F01.
