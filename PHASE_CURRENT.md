# IDEA — PHASE CURRENT

> Contrato operacional da fase ativa. O `ROADMAP.md` é a fonte canônica; este arquivo registra apenas estado e autorização.

## Fase ativa

**F09 — Exportação Markdown e restauração**

**Subetapas:** F09.01 implementada; aguardando auditoria independente.

## Trabalho autorizado agora

- executar auditoria independente de F09.01;
- corrigir somente findings se uma nova auditoria for solicitada;
- preservar decisões humanas, histórico, offline e D01–D09.

## Não autorizado

- iniciar F09.02+ ou registrar `G09` antes de auditoria PASS e autorização explícita;
- transformar sugestão de IA em decisão ou lock automático;
- alterar D01–D09;
- iniciar F11–F20 enquanto `G10 != PASS`.

## Gates

- G00: `PASS`;
- G01: `PASS`;
- G02: `PASS`;
- G03: `PASS`;
- G04: `PASS`;
- G05: `PASS`;
- G06: `PASS`;
- G07: `PASS`;
- G08: `PASS`;
- G09: `NOT_RUN`;
- G10: `NOT_RUN`.

## Critérios F09.01

1. Snapshot aceito renderiza Markdown canônico offline.
2. Entradas iguais produzem bytes iguais, preservando Unicode e relações.
3. Dados de outro `projectId` são rejeitados.

## Próxima ação

G08 foi autorizado pela Product Authority após PASS independente de F08 no SHA `dfecf412d58168370efeb5035985d1318e94f321`. F09.01 está implementada e aguarda auditoria; não iniciar F09.02+ nem registrar `G09`.
