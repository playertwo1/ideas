# F01.01 — Mapa revisável de telas

Este mapa define o fluxo nominal do Idea no MVP 0.1. Ele descreve telas e
transições para revisão de produto; não define wireframes, componentes,
implementação Android, persistência ou integração com provider.

## Fluxo nominal

```text
Home → Nova ideia → Projeto
                 ↘ Entrevista → Decisões → Escopo → Spec → Roadmap → Exportação
                      ↑            ↑          ↑       ↑       ↑          ↓
                      └────────────┴──────────┴───────┴───────┴──── Projeto/Home
```

Uma transição pode retornar a `Projeto` para preservar o ponto de orientação.
`Home` também pode abrir um projeto existente diretamente.

## Mapa das telas

| Tela | Objetivo | Entradas | Saídas | Ação principal | Transições |
|---|---|---|---|---|---|
| **Home** | Mostrar projetos recentes, rascunhos e a próxima ação de cada projeto. | Lista de projetos, status de revisão, pendências e rascunhos. | Projeto selecionado ou intenção de criar projeto. | Abrir/retomar um projeto ou iniciar `Nova ideia`. | `Nova ideia`; `Projeto`; permanece em `Home`. |
| **Nova ideia** | Capturar a intenção original e iniciar um projeto. | Título, texto da ideia original, modo solicitado e limites conhecidos. | Rascunho inicial, snapshot da ideia original e configuração inicial do projeto. | Salvar a ideia e continuar. | `Projeto`; voltar para `Home` sem concluir. |
| **Projeto** | Servir como hub do projeto e orientar a próxima etapa disponível. | Snapshot atual, decisões, lacunas, escopo, spec, roadmap, gates e estado de revisão. | Área de trabalho escolhida e contexto de retorno ao hub. | Continuar a próxima etapa indicada. | `Entrevista`; `Decisões`; `Escopo`; `Spec`; `Roadmap`; `Exportação`; `Home`. |
| **Entrevista** | Esclarecer lacunas relevantes antes de fechar decisões e escopo. | Ideia original, problema, público, hipótese, perguntas, respostas, desconhecidos e sugestões. | Respostas registradas, lacunas atualizadas e propostas encaminhadas para revisão. | Responder a próxima pergunta relevante e salvar o avanço. | `Decisões` quando houver proposta; `Projeto`; permanece em `Entrevista`. |
| **Decisões** | Revisar alternativas, rationale, autoria e estado das decisões. | Respostas da entrevista, sugestões da IA, opções, trade-offs e decisões vigentes. | Decisão aceita/editada, nova revisão e, somente por ação humana explícita, decisão `LOCKED`. | Revisar e confirmar uma decisão. | `Entrevista`; `Escopo`; `Projeto`; permanece em `Decisões`. |
| **Escopo** | Definir o corte do MVP e tornar explícitos os itens fora do corte. | Decisões vigentes, candidatos de feature, prioridades, dependências e rationale. | Escopo MVP, itens fora do escopo, atribuições de prioridade e cobertura pretendida. | Confirmar o corte do MVP. | `Decisões`; `Spec`; `Roadmap`; `Projeto`. |
| **Spec** | Revisar o que será construído em termos verificáveis. | Escopo confirmado, decisões, requisitos, NFRs, acceptance e restrições. | Revisão da spec com requisitos, critérios de aceitação e pendências visíveis. | Revisar e confirmar a spec. | `Escopo`; `Roadmap`; `Exportação`; `Projeto`. |
| **Roadmap** | Organizar fases e subetapas em ordem compreensível e verificável. | Escopo, requisitos, dependências, entregas, critérios `verify` e rationale. | Roadmap ordenado, dependências identificadas e próxima etapa de execução documentada. | Revisar e confirmar o roadmap. | `Spec`; `Exportação`; `Projeto`. |
| **Exportação** | Preparar e entregar uma visão versionada do projeto para uso fora do Idea. | Snapshot selecionado, spec, roadmap, referências, gates, limitações e manifest. | Prévia, resultado da exportação e pacote versionado ou erro acionável. | Revisar a prévia e exportar o pacote. | `Projeto` após concluir/cancelar; `Home` após finalizar. |

## Regras de revisão do mapa

- Toda tela tem um objetivo único e uma ação principal identificável.
- `Nova ideia` inicia o fluxo preservando a intenção original; interpretação e
  sugestões permanecem distinguíveis.
- `Projeto` é o hub de orientação, não uma nova fonte de verdade.
- `Decisões` não transforma sugestão da IA em `LOCKED` sem autoridade humana.
- `Escopo`, `Spec` e `Roadmap` formam uma sequência revisável, mas podem ser
  reabertos pelo projeto quando uma revisão anterior exigir retorno.
- `Exportação` representa o snapshot escolhido e não inicia execução de agentes.

## Verificação de F01.01

As nove telas exigidas estão presentes: Home, Nova ideia, Projeto, Entrevista,
Decisões, Escopo, Spec, Roadmap e Exportação. Cada linha contém objetivo,
entradas, saídas, ação principal e transições. F01.02 (wireframes e estados
visuais) não foi iniciado.
