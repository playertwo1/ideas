# M0 — Governança e fronteiras

Este documento materializa F00.07–F00.08 sem substituir `ROADMAP.md`.

## Core e Factory

Idea Core: Capture → Clarify → Decide → Cut → Specify → Plan → Export.

Idea Factory: Research → Validate → Blueprint → Task DAG → Context Compiler → Guardrails → Independent Review → Bootstrap → Agent Handoff.

O Core deve provar valor antes da implementação da Factory. Preparação documental e pesquisa não equivalem a autorização para construir capacidades F11–F20.

## G10

G10 é a fronteira constitucional de expansão. Antes de G10 PASS, capacidades F11–F20 não entram em implementação do produto. Se o Core não demonstrar redução de ambiguidade e utilidade do pacote, o retorno é ao núcleo, especialmente entrevista/decisões, corte/especificação e planejamento, em vez de adicionar sofisticação.

## Progressive Commitment

CAPTURED → SUGGESTED → ACCEPTED → LOCKED, com relevância de implementação registrada separadamente quando necessário. A IA não promove conteúdo para LOCKED: o contrato exige `authority=USER` e `lockAction=HUMAN_EXPLICIT`, mantendo autoria e autoridade distintas. Mudança LOCKED gera revisão e impacto.

## Builder → Auditor → Product Authority

Builder executa autonomamente o contrato da fase ativa, verifica e produz evidências. Auditor revisa de forma independente, procurando falhas de escopo, acceptance, regressão, segurança e evidência. Finding corrigível dentro do contrato retorna ao Builder sem nova autorização. Product Authority decide novidade material, mudança de escopo, risco novo relevante, ação externa/destrutiva não autorizada, conflito sem regra e o registro final dos gates Gxx.

`AUDIT RESULT: PASS` não troca fase sozinho. Aprovação humana não transforma check técnico não executado em PASS.

## Portabilidade

Android é o target do MVP, não uma dependência permitida para as regras fundamentais do domínio. IDs, estados, decisões, gates, validações, rastreabilidade, contratos de IA e formatos de pacote permanecem independentes de APIs Android. Persistência, arquivos, credenciais, rede e UI ficam atrás das fronteiras apropriadas.

Não criar backend, sincronização, módulos multiplataforma ou framework cross-platform apenas para antecipar um segundo cliente. Uma dependência Android no Core exige justificativa arquitetural; extração física para código compartilhado só ocorre quando houver segundo cliente real ou decisão posterior ao G10.
