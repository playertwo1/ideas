# REFERENCE_MATRIX — Idea

> Consolidação da pesquisa que influenciou o Idea. Registra padrões estudados, não dependências nem autorização automática para copiar código. A redação canônica do produto permanece em `ROADMAP.md` v0.6.

| ID | Referência | Uso | Aproveitar | Evitar | Reuso de código |
|---|---|---|---|---|---|
| REF-01 | Enterprise-DNA-OS/ai-app-idea-generator | ADAPT | intake; ideia → perguntas → spec | stack web/Supabase como requisito | NÃO AUTORIZADO sem verificação específica |
| REF-02 | mohdhd/vibe-architect | ADAPT | Propose → Refine → Lock; trade-offs | UI/React obrigatória; lock pela IA | NÃO AUTORIZADO sem verificação específica |
| REF-03 | BuildGreatProducts/builder-os | INSPIRE | capacidades encadeáveis | Build/Launch automático no Core | NÃO AUTORIZADO sem verificação específica |
| REF-04 | agentgill/spec-driven-development | ADAPT | REQ → TASK → VERIFY; acceptance | reduzir Idea a três docs | NÃO AUTORIZADO sem verificação específica |
| REF-05 | specd-sdd/SpecD | INSPIRE | contexto compilado; consistência; impacto | grafo spec↔código no MVP | NÃO AUTORIZADO sem verificação específica |
| REF-06 | cdeust/ai-prd-generator | ADAPT | clarificação; readiness; rastreabilidade | confiança cosmética; cópia sem licença | NÃO AUTORIZADO sem verificação específica |
| REF-07 | specdd/specdd | ADAPT | specs locais; ownership; invariantes | fragmentação sem visão global | NÃO AUTORIZADO sem verificação específica |
| REF-08 | longcipher/pb-spec | ADAPT | Plan → Build → Verify; DAG; evaluator separado | TDD/mutation testing universal | NÃO AUTORIZADO sem verificação específica |

## Estado

- Os oito projetos foram estudados como referências conceituais e os padrões úteis foram traduzidos para o `ROADMAP.md`.
- Nenhum é dependência obrigatória.
- O agente não precisa consultar os repositórios externos para descobrir o comportamento esperado do Idea.
- **Licença/proveniência para reuso de código não é considerada verificada por esta matriz.** Verificar somente quando houver componente concreto candidato.
- Padrão externo não convertido em requisito, contrato, acceptance ou fase do Idea não ganha força normativa por aparecer aqui.

**REFERÊNCIA EXPLICA ORIGEM; ROADMAP/CONTRATOS DEFINEM COMPORTAMENTO.**
