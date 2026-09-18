# IDEA — PHASE CURRENT

> Contrato operacional da fase ativa. O `ROADMAP.md` é a fonte canônica; este arquivo registra apenas estado e autorização.

## Fase ativa

**F07 — Hipótese mínima, MVP Cutter e requisitos**

**Subetapas:** F07.01–F07.06 autorizadas, aguardando implementação.

## Trabalho autorizado agora

- implementar F07.01–F07.06;
- executar até três rodadas Builder↔Auditor, parando no primeiro PASS;
- após três FAIL, parar e escalar sem registrar G07;
- preservar decisões humanas, histórico, offline e D01–D09.

## Não autorizado

- iniciar F08 ou registrar `G07` antes da auditoria independente;
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
- G07: `NOT_RUN`;
- G10: `NOT_RUN`.

## Critérios F07

1. Ficha de problema, hipótese, teste barato, métrica e limiar.
2. Features MUST/SHOULD/COULD/LATER/REJECTED com justificativas.
3. Fluxo ponta a ponta mínimo e non-goals explícitos.
4. REQ/NFR com IDs estáveis, origem e acceptance.
5. Jornadas, estados e erros relevantes.
6. Prévia, aprovação de lote, cobertura e duplicação.

## Próxima ação

Implementação de F07 seguida de auditoria independente. Máximo de três rodadas; não iniciar F08 nem registrar `G07 = PASS` sem PASS do Auditor e ação da Product Authority.
