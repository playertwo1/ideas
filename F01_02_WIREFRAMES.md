# F01.02 — Wireframes revisáveis

Wireframes estruturais do fluxo definido em `F01_01_SCREEN_MAP.md`. Eles são
independentes de toolkit e não definem componentes Android, persistência,
provider, regras de autosave ou comportamento detalhado de navegação de F01.03.

## Convenções

```text
┌──────────────────────────────────┐
│ Tela                    [estado] │  título e condição operacional
├──────────────────────────────────┤
│ conteúdo e contexto              │
├──────────────────────────────────┤
│ [ação secundária] [ação principal]│
└──────────────────────────────────┘
```

- A ação principal fica no rodapé; voltar ao `Projeto` permanece visível nas
  telas internas.
- `Offline` não bloqueia leitura, edição, conferência ou exportação local.
  Somente ações que exigem IA ficam indisponíveis e explicam como continuar.
- Erros nunca substituem conteúdo existente por vazio.
- Uma interrupção mostra o último ponto reconhecido e oferece `Retomar`.

## Fluxo principal

```text
Home ──Nova ideia──> Nova ideia ──Salvar e continuar──> Projeto
  ^                                                      │
  │                                                      v
  └──────────── Projeto <── Entrevista → Decisões → Escopo → Spec → Roadmap
                    ^                                              │
                    └────────────── Exportação <────────────────────┘
```

### Home

```text
┌──────────────────────────────────┐
│ Idea                     [local] │
├──────────────────────────────────┤
│ Projetos                         │
│ Projeto A · Entrevista · Retomar │
│ Projeto B · Pronto para exportar │
├──────────────────────────────────┤
│                  [Nova ideia]    │
└──────────────────────────────────┘
```

### Nova ideia

```text
┌──────────────────────────────────┐
│ Nova ideia              [rascunho]│
├──────────────────────────────────┤
│ Título                           │
│ [______________________________] │
│ Ideia original                   │
│ [______________________________] │
│ [______________________________] │
│ Modo: (LIGHT) (STANDARD) (DEEP)  │
├──────────────────────────────────┤
│ [Cancelar]   [Salvar e continuar]│
└──────────────────────────────────┘
```

### Projeto

```text
┌──────────────────────────────────┐
│ Projeto A              [em revisão]│
├──────────────────────────────────┤
│ Próxima etapa: Entrevista         │
│ [Retomar entrevista]              │
│                                   │
│ Entrevista  3 pendências          │
│ Decisões    1 para revisar        │
│ Escopo      não confirmado        │
│ Spec        rascunho              │
│ Roadmap     pendente              │
│ Exportação  indisponível: revisar │
├──────────────────────────────────┤
│ [Home]          [Continuar etapa] │
└──────────────────────────────────┘
```

### Entrevista

```text
┌──────────────────────────────────┐
│ Entrevista             [3 de 6] │
├──────────────────────────────────┤
│ Qual problema precisa ser        │
│ resolvido primeiro?              │
│ [______________________________] │
│ [______________________________] │
│ Lacuna relacionada: público      │
├──────────────────────────────────┤
│ [Projeto]        [Salvar resposta]│
└──────────────────────────────────┘
```

### Decisões

```text
┌──────────────────────────────────┐
│ Decisões            [1 para revisar]│
├──────────────────────────────────┤
│ Público inicial                   │
│ Sugestão IA · ainda não aceita    │
│ Opções e trade-offs               │
│ Rationale [____________________]  │
│ [Editar] [Aceitar como hipótese]  │
│ [Fechar decisão — ação humana]    │
├──────────────────────────────────┤
│ [Projeto]      [Revisar próxima]  │
└──────────────────────────────────┘
```

### Escopo

```text
┌──────────────────────────────────┐
│ Escopo                 [rascunho]│
├──────────────────────────────────┤
│ Dentro do MVP                    │
│ ☑ Capturar ideia                 │
│ ☑ Revisar decisões               │
│ Fora do MVP                      │
│ ☐ Sincronização · rationale      │
│ Pendência: 1 item sem prioridade │
├──────────────────────────────────┤
│ [Projeto]       [Confirmar escopo]│
└──────────────────────────────────┘
```

### Spec

```text
┌──────────────────────────────────┐
│ Spec                   [rascunho]│
├──────────────────────────────────┤
│ REQ-001 Capturar ideia     [Editar]│
│ Acceptance: definido             │
│ REQ-002 Revisar decisão    [Editar]│
│ Acceptance: pendente             │
│ 1 pendência impede confirmação   │
├──────────────────────────────────┤
│ [Projeto]         [Revisar spec] │
└──────────────────────────────────┘
```

### Roadmap

```text
┌──────────────────────────────────┐
│ Roadmap                [rascunho]│
├──────────────────────────────────┤
│ Fase 1 · Intake                  │
│  └ entrega / verificação         │
│ Fase 2 · Decisões                │
│  └ depende de Fase 1             │
│ [Editar fase] [Reordenar]        │
├──────────────────────────────────┤
│ [Projeto]      [Confirmar roadmap]│
└──────────────────────────────────┘
```

### Exportação

```text
┌──────────────────────────────────┐
│ Exportação            [prévia]   │
├──────────────────────────────────┤
│ Snapshot: revisão 7              │
│ Inclui: JSON, Markdown, manifest │
│ Avisos: 1 limitação documentada  │
│ [Examinar conteúdo]              │
│ Destino: armazenamento local     │
├──────────────────────────────────┤
│ [Projeto]      [Exportar pacote] │
└──────────────────────────────────┘
```

## Estados transversais revisáveis

| Estado | Apresentação | Ação principal | Recuperação esperada |
|---|---|---|---|
| Vazio | Explica o que ainda não existe sem simular conteúdo. | Criar a primeira ideia na Home; adicionar o primeiro item nas demais telas. | A criação conduz ao fluxo nominal e mantém retorno ao Projeto. |
| Erro | Mantém o último conteúdo legível e identifica a operação que falhou. | `Tentar novamente`; em exportação, também `Escolher outro destino`. | Repetir só a operação falha ou voltar ao Projeto sem perder conteúdo visível. |
| Offline | Faixa persistente `Offline`; edição e exportação local continuam disponíveis; geração por IA aparece indisponível. | `Continuar offline`; quando a ação exigir IA, `Tentar quando online`. | Reconexão remove a faixa sem aplicar sugestão ou alteração automaticamente. |
| Interrompido | Cartão com etapa, último ponto reconhecido e trabalho ainda não concluído. | `Retomar`; alternativa explícita `Voltar ao Projeto`. | Retorna ao ponto mostrado; não afirma que entrada incerta foi salva. |

## Aplicação dos estados por tela

| Tela | Vazio | Erro | Offline | Interrompido |
|---|---|---|---|---|
| Home | `Nenhum projeto` + `Nova ideia`. | Lista preservada + falha localizada. | Projetos locais acessíveis. | Projeto mostra `Retomar de Entrevista/edição/exportação`. |
| Nova ideia | Campos em branco com orientação mínima. | Entrada permanece visível; falha de operação identificada. | Criação e edição continuam locais. | Retomar mostra campos reconhecidos e aviso sobre conteúdo incerto. |
| Projeto | Etapas ainda não iniciadas, sem progresso fictício. | Bloco afetado sinalizado; demais etapas acessíveis. | Leitura, edição e exportação local disponíveis. | Próxima ação vira `Retomar <etapa>`. |
| Entrevista | Nenhuma pergunta pendente + retorno ao Projeto. | Resposta visível; tentativa pode ser repetida. | Respostas locais continuam; nova geração por IA aguarda rede. | Mostra pergunta e resposta reconhecidas antes de retomar. |
| Decisões | Nenhuma decisão para revisar + retorno/seguir. | Decisão vigente preservada; proposta falha não é aplicada. | Revisão humana continua; IA indisponível. | Retoma a decisão em revisão sem promovê-la de estado. |
| Escopo | Nenhum item definido + adicionar item. | Corte vigente preservado e erro localizado. | Edição e confirmação locais disponíveis. | Retoma itens reconhecidos; seleção incerta não é presumida. |
| Spec | Nenhum requisito + adicionar requisito. | Requisitos existentes permanecem legíveis. | Edição e conferência locais disponíveis. | Retoma o requisito indicado e mostra pendências. |
| Roadmap | Nenhuma fase + adicionar fase. | Fases existentes preservadas; operação falha identificada. | Edição e conferência locais disponíveis. | Retoma fase/subetapa indicada sem reordenar automaticamente. |
| Exportação | Explica pré-requisitos ausentes e liga para a etapa editável. | Falha de pacote/destino com nova tentativa segura. | Exportação para armazenamento local permanece disponível. | Mostra exportação não concluída e permite retomar ou voltar ao Projeto. |

## Verificação de F01.02

- As nove telas e transições preservam `F01_01_SCREEN_MAP.md`.
- No fluxo nominal, o usuário encontra `Retomar` na Home/Projeto, `Editar` nas
  etapas revisáveis e `Exportar pacote` na Exportação.
- Vazio, erro, offline e interrompido têm apresentação, ação e recuperação
  explícitas, sem antecipar regras de salvar/voltar/rascunho de F01.03.
- Nenhum wireframe define Android, Compose, Room, Gradle, provider ou Factory.
