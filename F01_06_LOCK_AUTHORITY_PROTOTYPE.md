# F01.06 — Protótipo de autoridade para LOCK

Protótipo textual revisável para distinguir sugestão, hipótese aceita e decisão
humana fechada. Aplica `PROJECT_STANDARD.md` e preserva D01–D09; não implementa
UI, Android, persistência ou integração com provider.

## Estados e autoridade

| Estado | Significado visível | Quem pode produzir | Efeito |
|---|---|---|---|
| `SUGGESTED` | Proposta ainda não aceita. | IA, sistema ou pessoa. | Não governa escopo nem substitui decisão vigente. |
| `ACCEPTED` | Hipótese/default aceito para prosseguir, ainda revisável. | Pessoa por ação explícita. | Pode orientar trabalho, mas permanece distinguível de decisão fechada. |
| `LOCKED` | Decisão humana fechada para a revisão identificada. | Somente autoridade humana explícita. | Governa derivados; alteração exige nova revisão e análise de impacto. |
| `IMPLEMENTATION_RELEVANT` | Decisão fechada alcançou trabalho de implementação. | Sistema por regra determinística sobre uma decisão `LOCKED`. | Não concede nova autoridade e não altera a revisão fechada. |

Autoria e autoridade são campos separados. Conteúdo `AI`, `SYSTEM`, `EXTERNAL`
ou `IMPORTED` não se torna `USER` por exibição, edição automática ou aceitação
implícita.

## Cartão de decisão

```text
┌────────────────────────────────────────┐
│ Público inicial              SUGGESTED │
│ Origem: AI · não é decisão humana      │
├────────────────────────────────────────┤
│ Proposta: time pequeno                 │
│ Rationale: reduz suporte no piloto     │
│ Evidência/entrada: resposta Q-003      │
│ Alternativas e trade-offs              │
├────────────────────────────────────────┤
│ [Editar] [Rejeitar] [Aceitar hipótese] │
└────────────────────────────────────────┘
```

O estado, a origem e a consequência aparecem antes da ação. Cor, ícone ou ordem
visual nunca são a única forma de distinguir autoridade.

## Aceitar como hipótese

```text
SUGGESTED ──[Aceitar hipótese]──> confirmação curta ──> ACCEPTED
```

A confirmação identifica decisão, valor e estado resultante. Ela não oferece
`LOCKED` como efeito colateral. Editar uma sugestão cria conteúdo humano em nova
revisão, preservando a origem da sugestão anterior.

## Fechar decisão

`Fechar decisão` é uma ação humana separada, disponível somente em conteúdo
revisado e com rationale não vazio.

```text
┌────────────────────────────────────────┐
│ Fechar decisão?                        │
├────────────────────────────────────────┤
│ Decisão: Público inicial               │
│ Valor: time pequeno                    │
│ Revisão: REV-007                       │
│                                        │
│ Ao fechar, esta decisão governará      │
│ escopo, requisitos e roadmap derivados.│
│ Mudanças futuras criarão nova revisão. │
├────────────────────────────────────────┤
│ [Cancelar]       [Fechar como LOCKED]  │
└────────────────────────────────────────┘
```

Regras:

- não existe fechamento automático, em lote implícito ou por resposta da IA;
- o comando inclui `decisionId` e revisão esperada;
- revisão obsoleta retorna conflito e não grava;
- cancelar preserva exatamente o estado anterior;
- sucesso registra nova revisão, autoridade humana, rationale e evento atômico;
- `LOCKED` não pode sofrer atualização destrutiva.

## Apresentação de decisão fechada

```text
┌────────────────────────────────────────┐
│ Público inicial                  LOCKED│
│ Autoridade: USER · revisão REV-007     │
├────────────────────────────────────────┤
│ Escolha: time pequeno                  │
│ Rationale: reduzir suporte no piloto   │
│ Fechada em: <timestamp registrado>     │
├────────────────────────────────────────┤
│ [Ver histórico] [Propor mudança]       │
└────────────────────────────────────────┘
```

Não há ação `Editar` direta. `Propor mudança` inicia uma proposta separada e
mantém a revisão `LOCKED` vigente até nova confirmação humana.

## Propor mudança com impacto

```text
LOCKED vigente
      │ [Propor mudança]
      v
Proposta DRAFT/SUGGESTED separada
      │ motivo obrigatório + novo valor
      v
Análise determinística de impacto
      │
      ├─ decisões/requisitos/escopo/roadmap afetados
      ├─ artefatos e gates potencialmente STALE
      └─ itens sem impacto conhecido, explicitamente separados
      v
Revisão humana do delta
      │
      ├─ [Cancelar] → mantém LOCKED vigente
      └─ [Confirmar mudança] → nova revisão LOCKED + invalida derivados afetados
```

### Tela de impacto

```text
┌────────────────────────────────────────┐
│ Revisar mudança de decisão             │
├────────────────────────────────────────┤
│ Atual: time pequeno · REV-007 · LOCKED │
│ Proposta: público aberto               │
│ Motivo: ampliar piloto                 │
│                                        │
│ Impactos conhecidos                    │
│ • Escopo: FEAT-004 precisa revisão     │
│ • Requisito: REQ-012 fica STALE        │
│ • Roadmap: PHASE-003 precisa revisão   │
│ • Gate: GATE-002 deve ser reexecutado  │
│                                        │
│ Impacto desconhecido: nenhum detectado │
├────────────────────────────────────────┤
│ [Cancelar] [Confirmar nova revisão]    │
└────────────────────────────────────────┘
```

Se a análise não puder ser concluída, a interface mostra a incerteza e não
oferece falsa alegação de impacto completo. A pessoa pode cancelar ou manter a
proposta pendente; não substitui a decisão vigente.

## Matriz de ações

| Estado atual | Ação | Estado/resultante | Permitido para IA? |
|---|---|---|---|
| `SUGGESTED` | Editar | Nova revisão humana ainda não fechada | Não executar; apenas sugerir texto. |
| `SUGGESTED` | Aceitar hipótese | `ACCEPTED` | Não; exige ação humana. |
| `SUGGESTED`/`ACCEPTED` | Fechar decisão | `LOCKED` após confirmação | Nunca. |
| `LOCKED` | Editar diretamente | Bloqueado | Nunca. |
| `LOCKED` | Propor mudança | Proposta separada; vigente preservada | Pode sugerir proposta, sem aplicar. |
| Proposta de mudança | Confirmar | Nova revisão `LOCKED`; anterior preservada no histórico | Nunca. |
| Qualquer estado | Definir gate `PASS` | Fora deste fluxo | Nunca. |

## Falhas e recuperação

| Falha | Resposta obrigatória |
|---|---|
| Revisão esperada não é a vigente | Mostrar conflito, recarregar comparação e não gravar. |
| Falha ao persistir fechamento | Manter estado anterior; não mostrar `LOCKED`. |
| Interrupção durante confirmação | Na retomada, consultar revisão confirmada; nunca inferir sucesso pelo clique. |
| Resultado de IA atrasado | Manter como sugestão separada vinculada à revisão de entrada. |
| Impacto parcial/desconhecido | Marcar incompleto e impedir alegação de revisão integral. |
| Cancelamento | Remover apenas a proposta pendente selecionada; preservar revisão vigente. |

## Cenários verificáveis

1. Sugestão de IA aparece como `SUGGESTED`, com origem `AI`, sem ação automática.
2. Aceitar a sugestão resulta em `ACCEPTED`, nunca em `LOCKED`.
3. Somente `Fechar como LOCKED`, após confirmação humana, fecha a decisão.
4. Cancelar o fechamento não cria revisão nem evento de fechamento.
5. Fechamento com revisão esperada antiga falha sem alterar a vigente.
6. Uma decisão `LOCKED` não apresenta edição direta; mudança cria proposta.
7. Cancelar proposta de mudança conserva a revisão `LOCKED` anterior.
8. Confirmar mudança cria nova revisão e mantém histórico da anterior.
9. Derivados impactados aparecem para revisão/invalidação; gate não vira `PASS`.
10. Interrupção ou falha de gravação nunca produz falso estado fechado.

## Verificação de F01.06

- O protótipo distingue textual e semanticamente `SUGGESTED`, `ACCEPTED` e
  `LOCKED`.
- Autoridade humana explícita é obrigatória para fechar ou substituir decisão.
- Mudança de decisão fechada preserva histórico e exige impacto revisável.
- IA pode propor, mas não atribui ID definitivo, autoridade, `LOCKED` ou gate
  `PASS`.
- F01.07, Android, provider e Idea Factory não foram iniciados.
