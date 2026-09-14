# M0 — Context Efficiency Baseline

> Artefato de F00 para organizar como o próprio repositório Idea é consumido por agentes. Não implementa a futura F15 do produto, não muda D01–D09 e não autoriza F11–F20.

## Problema observado

O Idea já possui boa separação entre estado, fase, watchdog, auditoria, plano e roadmap, porém parte das mesmas regras e do mesmo estado aparece em vários documentos. Para agentes, isso cria dois custos:

1. contexto repetido é reenviado/relido mesmo quando não é necessário à tarefa;
2. estado dinâmico duplicado pode divergir e produzir ambiguidade.

Exemplo já observado antes desta baseline: `README.md` ainda declarava F00/F00.01 enquanto `PROJECT_STATE.md` já apontava F00.08 concluída e G00 como próxima atividade.

## Decisão operacional

Adotar **Minimum Sufficient Context** no desenvolvimento do próprio Idea.

A política é materializada por:

- `CONTEXT_POLICY.md` — regras de orçamento, leitura progressiva, delta/fingerprint e métricas;
- `AI_CONTEXT_INDEX.md` — roteamento humano/agent-friendly por tipo de tarefa;
- `context-manifest.json` — roteamento equivalente em formato estruturado;
- `AGENTS.md` — bootstrap mínimo e autoridade;
- `PROJECT_STATE.md` — estado dinâmico único para retomada;
- `PHASE_CURRENT.md` — autorização da fase, sem histórico duplicado.

## Casas canônicas

| Informação | Fonte |
|---|---|
| Estado atual / próxima ação | `PROJECT_STATE.md` |
| Autorização e limites da fase | `PHASE_CURRENT.md` |
| Checklist completo | `EXECUTION_PLAN.md` |
| Contrato/decisão canônica | `ROADMAP.md` |
| Regras estruturais materializadas | `PROJECT_STANDARD.md` |
| Evidência de F00 | `M0_VALIDATION_EVIDENCE.md` |
| Referências externas | `REFERENCE_MATRIX.md` |
| Segurança/anti-drift de alto risco | `WATCHDOG.md` |
| Método de auditoria | `AUDIT.md` |
| Política de contexto | `CONTEXT_POLICY.md` |

Outros documentos referenciam essas fontes; não devem manter cópias dinâmicas desnecessárias.

## Compatibilidade com o roadmap

Esta baseline apenas antecipa como prática interna princípios que o roadmap já contém:

- F04.07: limites de custo/tokens e envio mínimo de contexto;
- F15: Context Compiler por tarefa, orçamento, deduplicação, fingerprint e contexto específico;
- F16/F17: guardrails pequenos e revisão independente.

Ela não conta como implementação nem conclusão dessas fases futuras.

## Critérios de sucesso desta baseline

- bootstrap padrão não exige ROADMAP, WATCHDOG, AUDIT ou EXECUTION_PLAN completos;
- ROADMAP inteiro deixa de ser leitura inicial normal;
- status dinâmico não é duplicado no README;
- Watchdog é carregado por gatilho de risco;
- Audit é carregado para Auditor/gate/revisão material;
- contexto obrigatório nunca é truncado silenciosamente para economizar tokens;
- um agente consegue descobrir que arquivo adicional precisa ler usando `AI_CONTEXT_INDEX.md`/manifest;
- G00 continua sob auditoria independente e não é autoaprovado por esta mudança.

## Métricas futuras

Quando houver infraestrutura para medi-las, usar:

- Critical Context Recall;
- Irrelevant Context Rate;
- Context Compression Ratio;
- input/output/cached tokens quando o provider expuser;
- bytes/blocos de contexto;
- latência.

A meta constitucional é Critical Context Recall de 100% no corpus homologado. Compressão é otimização secundária.

## Regra final

**O Idea não deve confundir documentação completa com contexto obrigatório.**

O conhecimento pode ser extenso; o pacote entregue a uma tarefa deve ser mínimo, suficiente, rastreável e ampliável sob demanda.
