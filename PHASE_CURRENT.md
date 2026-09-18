# IDEA — PHASE CURRENT

> Contrato operacional da fase ativa. O `ROADMAP.md` é a fonte canônica; este arquivo registra apenas estado e autorização.

## Fase ativa

**F06 — Entrevista inteligente e decisões versionadas**

**Subetapas:** F06.01–F06.07 autorizadas, ainda não implementadas.

## Trabalho autorizado agora

- implementar F06.01–F06.07;
- manter entrevista offline por padrão;
- preservar decisões humanas, rastreabilidade e D01–D09;
- atualizar evidência e estado somente com mudanças reais.

## Não autorizado

- iniciar F07 ou registrar `G06` antes da auditoria de F06;
- transformar sugestão de IA em decisão ou lock automático;
- alterar D01–D09;
- iniciar F11–F20 enquanto `G10 != PASS`.

## Gates

- G00: `PASS`;
- G01: `PASS`;
- G02: `PASS`;
- G03: `PASS`;
- G04: `PASS`;
- G05: `PASS` — Product Authority autorizou F06;
- G06: `NOT_RUN`;
- G10: `NOT_RUN`.

## Critérios F06

1. Lacunas catalogadas por tipo, modo e criticidade.
2. Rodadas adaptativas sem repetição desnecessária.
3. Responder, editar, adiar e aceitar default explícito.
4. DecisionRevision com Propose/Refine/Lock humano.
5. Reabertura com delta e invalidação de derivados.
6. Stop condition e orçamento de rodadas.
7. Histórico legível com autoria e rastreabilidade.

## Próxima ação

Implementar F06.01–F06.07 e parar para auditoria independente. Não iniciar F07 nem registrar `G06 = PASS`.
