# IDEA — PHASE CURRENT

> Contrato operacional da fase ativa. O `ROADMAP.md` é a fonte canônica; este arquivo só registra o estado corrente e a autorização vigente.

## Fase ativa

**F05 — Captura e interpretação inicial**

**Subetapas:** F05.01–F05.05 implementadas, aguardando auditoria independente.

## Trabalho autorizado agora

- auditar F05.01–F05.05;
- corrigir somente findings reproduzíveis de F05;
- manter execução offline por padrão e isolamento por `projectId`;
- preservar F00–F04 e D01–D09.

## Não autorizado

- iniciar F06 ou registrar `G05` antes da auditoria independente;
- alterar D01–D09;
- adicionar sincronização, upload ou IA além das portas já existentes;
- registrar aprovação de gate pelo Builder ou Auditor.

## Gates

- G00: `PASS`;
- G01: `PASS`;
- G02: `PASS`;
- G03: `PASS`;
- G04: `PASS` — Product Authority autorizou F05;
- G05: `NOT_RUN`;
- G10: `NOT_RUN`.

## Critérios F05

1. Entrada com título, ideia, limites, multiline e validação.
2. Snapshot original imutável com hash SHA-256.
3. Tipo, restrições, profundidade e motivo visíveis e corrigíveis.
4. Interpretação é sugestão editável; aceitar/rejeitar é explícito.
5. Progresso e próxima ação persistem por estado e `projectId`.

## Próxima ação

Auditoria independente de F05. Não iniciar F06 nem registrar `G05 = PASS`.
