# F01.03 — Navegação, salvar/voltar e recuperação de rascunho

Contrato de experiência para o fluxo aprovado em `F01_01_SCREEN_MAP.md` e os
wireframes de `F01_02_WIREFRAMES.md`. Este documento define comportamento
observável, sem implementar Android, persistência, autosave técnico ou provider.

## Invariantes

- Navegar nunca descarta entrada sem aviso.
- Conteúdo confirmado e conteúdo em edição permanecem distinguíveis.
- `Salvo` só aparece depois de confirmação real da gravação.
- Falha ao salvar preserva o conteúdo visível e oferece nova tentativa.
- Ler, editar, salvar, conferir e exportar continuam disponíveis offline.
- Recuperar não promove sugestão, hipótese ou decisão para `LOCKED`.
- Uma ação destrutiva exige alvo e consequência explícitos.

## Estados observáveis de edição

| Estado | Significado apresentado | Ações disponíveis |
|---|---|---|
| Sem alterações | A tela corresponde ao último conteúdo confirmado. | Editar, voltar ou seguir. |
| Alterações pendentes | Há entrada ainda não confirmada como salva. | Salvar, continuar editando ou tentar sair. |
| Salvando | A confirmação da gravação ainda não chegou. | Permanecer na tela; cancelamento não presume sucesso. |
| Salvo | A gravação foi confirmada, com indicação inequívoca. | Voltar, seguir ou editar novamente. |
| Falha ao salvar | O conteúdo continua visível, mas não recebeu garantia de durabilidade. | Tentar novamente, copiar conteúdo ou permanecer na tela. |
| Rascunho recuperável | Uma edição anterior não concluída foi encontrada. | Retomar rascunho, examinar versão confirmada ou descartar com confirmação. |

## Regras de salvar e voltar

1. `Salvar` confirma somente a edição da tela atual; não fecha decisões, gates ou
   etapas por consequência implícita.
2. `Salvar e continuar` só navega depois da confirmação da gravação. Em falha,
   permanece na tela com a entrada intacta.
3. `Voltar` ou retorno ao `Projeto`, sem alterações pendentes, navega diretamente.
4. Com alterações pendentes, tentar sair apresenta três escolhas: `Salvar e
   sair`, `Continuar editando` e `Descartar alterações`.
5. `Descartar alterações` nomeia a tela/registro afetado e exige confirmação;
   restaura a última versão confirmada, não um estado vazio inventado.
6. Fechar ou interromper durante `Salvando` não mostra falso sucesso. Na retomada,
   o sistema confronta o rascunho recuperável com a última versão confirmada.
7. A indisponibilidade de IA não impede gravação local nem retorno seguro.

## Destinos de navegação

| Origem | Ação | Destino | Condição de proteção |
|---|---|---|---|
| Home | `Nova ideia` | Nova ideia | Nenhuma entrada em edição. |
| Home | Abrir projeto | Projeto | Se houver interrupção, Projeto destaca `Retomar`. |
| Nova ideia | `Salvar e continuar` | Projeto | Navega somente após gravação confirmada. |
| Nova ideia | `Cancelar`/voltar | Home | Com entrada, exige salvar rascunho, continuar ou descartar. |
| Projeto | `Continuar etapa` | Etapa indicada | Abre o último ponto reconhecido quando houver interrupção. |
| Projeto | Abrir seção | Entrevista, Decisões, Escopo, Spec, Roadmap ou Exportação | Mantém Projeto como ponto de retorno. |
| Tela interna | `Projeto`/voltar | Projeto | Aplica as regras de alterações pendentes antes de sair. |
| Entrevista | `Salvar resposta` | Entrevista | Confirma a resposta; avançar pergunta não presume conclusão da etapa. |
| Decisões | `Revisar próxima` | Decisões | Salva a revisão atual antes de trocar o item. |
| Escopo | `Confirmar escopo` | Projeto ou Spec | Exige gravação confirmada e pendências bloqueantes resolvidas. |
| Spec | `Revisar spec` | Projeto ou Roadmap | Não descarta edição de requisito ao trocar de visão. |
| Roadmap | `Confirmar roadmap` | Projeto ou Exportação | Não reordena nem confirma silenciosamente ao sair. |
| Exportação | `Projeto`/cancelar | Projeto | Cancelar destino não altera o projeto nem apaga a prévia. |
| Exportação | Exportação concluída | Projeto ou Home | O projeto permanece editável e o snapshot exportado é identificado. |

## Recuperação de rascunho

### Entrada

Ao abrir Home ou Projeto, a existência de rascunho interrompido é indicada no
projeto correspondente, com tela, item e último ponto reconhecido. `Retomar`
leva a esse ponto; o usuário também pode abrir o Projeto sem aplicar o rascunho.

### Confronto seguro

Quando rascunho e versão confirmada coexistem, a tela mostra:

- qual é a versão confirmada;
- qual conteúdo pertence ao rascunho;
- se o último salvamento ficou sem confirmação;
- as ações `Retomar rascunho`, `Usar versão confirmada` e `Descartar rascunho`.

`Usar versão confirmada` não apaga o rascunho. `Descartar rascunho` exige
confirmação e informa que a versão confirmada será preservada.

### Recuperação por tela

| Tela | Ponto retomado | O que não pode ser presumido |
|---|---|---|
| Nova ideia | Campos reconhecidos e modo selecionado. | Que o projeto foi criado ou que texto incerto foi salvo. |
| Entrevista | Pergunta atual e resposta em edição. | Que a resposta foi aceita ou que a pergunta foi concluída. |
| Decisões | Decisão e alternativa em revisão. | Que sugestão foi aceita ou decisão tornou-se `LOCKED`. |
| Escopo | Item e classificação em edição. | Que o corte do MVP foi confirmado. |
| Spec | Requisito/acceptance em edição. | Que a spec foi revisada ou confirmada. |
| Roadmap | Fase/subetapa em edição. | Que ordem ou dependência foi confirmada. |
| Exportação | Snapshot e opções da prévia. | Que pacote foi criado, salvo ou compartilhado. |

Home e Projeto não recuperam conteúdo dentro de si: apresentam o ponto de
retomada correto. A recuperação nunca cruza projetos.

## Cenários verificáveis

| Cenário | Passos | Resultado esperado |
|---|---|---|
| Voltar sem alteração | Abrir uma tela interna e voltar. | Projeto abre sem diálogo e sem mudança de conteúdo. |
| Voltar com alteração | Editar e tentar voltar. | Escolhas de salvar, continuar ou descartar aparecem; nada é perdido silenciosamente. |
| Salvar com sucesso | Editar e escolher salvar. | `Salvo` aparece somente após confirmação; a navegação então é liberada. |
| Falha ao salvar | Editar, salvar e simular falha. | Entrada permanece visível; não aparece `Salvo`; nova tentativa é possível. |
| Interrupção antes da confirmação | Interromper durante `Salvando` e reabrir. | O projeto oferece `Retomar` e distingue rascunho da versão confirmada. |
| Descartar rascunho | Escolher descartar e cancelar a confirmação. | Rascunho permanece; somente confirmar executa o descarte. |
| Continuidade offline | Editar, salvar, voltar e exportar sem rede. | Operações locais continuam; apenas ação de IA explica indisponibilidade. |
| Isolamento | Interromper edição no Projeto A e abrir Projeto B. | Rascunho do A não aparece nem é aplicado no B. |
| Decisão em edição | Interromper revisão de sugestão e retomar. | Sugestão continua não aceita e não `LOCKED`. |
| Cancelar exportação | Abrir seletor/destino e cancelar. | Projeto e prévia permanecem; nenhum pacote é declarado exportado. |

## Verificação de F01.03

- Todas as transições do mapa e dos wireframes têm destino e proteção contra
  descarte silencioso.
- Salvar, voltar e recuperar possuem estados, escolhas e resultados observáveis.
- Os cenários cobrem sucesso, falha, interrupção, offline, descarte, isolamento e
  cancelamento de exportação.
- O contrato não define mecanismo de persistência, código Android nem arquitetura
  de F01.04.
