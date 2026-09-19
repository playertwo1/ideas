# IDEA — PHASE CURRENT

> Contrato operacional da fase ativa. O `ROADMAP.md` é a fonte canônica; este arquivo registra apenas estado e autorização.

## Fase ativa

**F08 — Roadmap básico e readiness do núcleo**

**Subetapas:** F08.01–F08.05 implementadas; aguardando auditoria independente.

## Trabalho autorizado agora

- executar auditoria independente de F08;
- corrigir somente findings se uma nova auditoria for solicitada;
- preservar decisões humanas, histórico, offline e D01–D09.

## Não autorizado

- iniciar F09 ou registrar `G08` antes de auditoria PASS e autorização explícita;
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
- G08: `NOT_RUN`;
- G10: `NOT_RUN`.

## Critérios F08

1. Phase/RoadmapItem com objetivo, entrega, verify, ordem e dependências.
2. Edição manual e reordenação sem referências quebradas.
3. Separação MVP/pós-MVP e pré-requisitos visíveis.
4. Checks determinísticos de órfãos, cobertura, referências, ciclos e revisões.
5. Readiness por dimensão; gate não executado continua pendente.

## Próxima ação

G07 foi autorizado pela Product Authority após PASS independente de F07. F08.01–F08.05 foram implementadas e aguardam auditoria; não iniciar F09 nem registrar `G08`.
