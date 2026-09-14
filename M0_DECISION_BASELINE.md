# M0 — Decision Baseline

Este arquivo materializa F00.06 sem substituir o contrato canônico de `ROADMAP.md`.

**Estado:** D01–D09 aprovadas/LOCKED pela Product Authority em 13/09/2026.

Qualquer alteração exige identificação do ID, proposta nova, motivo e análise de impacto antes de substituir a decisão vigente.

## D01 — Público e distribuição inicial

Uso pessoal/time pequeno; piloto por APK; Android-first.

**Fora do corte atual:** operação comercial pública, onboarding comercial, suporte em escala e backend exigido apenas por distribuição comercial.

## D02 — IA, credenciais e orçamento

Arquitetura provider-agnostic; `FakeProvider` obrigatório em testes e um provider real homologado no MVP. Codex CLI e Antigravity podem ser ferramentas do processo e futuros candidatos a adapters, mas não são dependências do Idea Core. Assinaturas pessoais não são presumidas como APIs programáticas.

## D03 — Offline

Ler, editar, salvar, conferir e exportar devem funcionar offline. IA permanece online no MVP.

## D04 — Recorte 0.1

O MVP 0.1 mantém o núcleo do produto e inclui ficha curta de hipótese/teste.

## D05 — DEEP na primeira versão

DEEP pode ser selecionado, mas capacidades futuras ainda indisponíveis ficam explicitamente pendentes. DEEP completo não é requisito do MVP 0.1.

## D06 — Portabilidade e edição externa

Exportação em Markdown/ZIP + JSON e restauração do próprio pacote. A edição canônica permanece no app. Importação arbitrária de Markdown fica fora do MVP 0.1.

## D07 — Equipe, capacidade e prazo

Milestones sem data rígida. Estimativas são recalibradas após persistência e IA; prazo fechado exige nova decisão de capacidade/corte.

## D08 — Projeto Vivo e distribuição 1.0

Projeto Vivo é o primeiro caso DEEP real após pilotos menores. Bootstrap Android é o perfil homologado inicialmente; outra stack exige perfil adicional e novo critério de handoff.

## D09 — Portabilidade futura

Android-first no MVP com Core platform-agnostic. Desktop/Web só são avaliados após G10. Não antecipar framework multiplataforma, backend ou sincronização para um segundo cliente ainda inexistente.

## Freeze do MVP 0.1

### Entra no Idea Core

`Capture → Clarify → Decide → Cut → Specify → Plan → Export`

Inclui preservação da ideia original, interpretação separada, entrevista proporcional, decisões versionadas, hipótese/teste mínimo, corte de escopo, REQ/NFR com acceptance, roadmap, checks/readiness, exportação Markdown/JSON/ZIP e restauração do próprio pacote.

### Não entra no MVP 0.1

Pesquisa automática profunda, Validator completo da Factory, geração completa de UX/Architecture Blueprint para projetos, Task DAG completo, Context Compiler, Guardrail Generator completo, Independent Review completo, Repository Bootstrap avançado, Agent Handoff avançado, execução contínua de agentes, Mission Control, colaboração/sync e clientes Desktop/Web.

## Critério de estabilidade

Este baseline só muda por decisão explícita da Product Authority. Refinamento técnico que preserve D01–D09 e o freeze acima não reabre o escopo.