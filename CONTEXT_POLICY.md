# CONTEXT_POLICY — Eficiência de contexto do Idea

> Política operacional do desenvolvimento do Idea. Não altera decisões D01–D09, gates ou escopo de produto. Complementa `AGENTS.md` e materializa, no próprio repositório, o princípio de contexto mínimo suficiente já previsto em F04.07/F15.

## 1. Objetivo

Usar o menor contexto suficiente para executar corretamente cada tarefa, sem sacrificar integridade, segurança, rastreabilidade ou evidência.

Ordem de prioridade:

**correção → integridade dos dados → regras críticas → evidência → eficiência.**

Economia de tokens nunca autoriza omitir regra necessária, inventar fato, pular gate ou reduzir validação exigida.

## 2. Camadas de contexto

### L0 — Bootstrap
Contexto mínimo para saber onde estamos e o que está autorizado.

Por padrão:
- `AGENTS.md`
- `PROJECT_STATE.md`
- pedido vigente da Product Authority

### L1 — Task Context
Arquivos diretamente necessários à tarefa atual. Descobertos por `AI_CONTEXT_INDEX.md` e/ou por busca de símbolo, ID, erro ou artefato.

### L2 — Dependency Context
Contratos, dependências, callers, schemas ou regras transitivas que só entram quando uma dependência concreta é identificada.

### L3 — Deep Reference
Roadmap completo, histórico, DOCX de pesquisa, auditorias antigas, referências externas e material de investigação. Só carregar quando L0–L2 forem insuficientes ou a tarefa exigir explicitamente.

## 3. Leitura progressiva

Antes de abrir arquivos adicionais, o agente deve conseguir responder: **qual pergunta concreta esta leitura pretende resolver?**

Ordem preferida:
1. localizar por ID, símbolo, arquivo, erro ou termo;
2. ler o menor trecho suficiente;
3. ler o arquivo completo quando necessário;
4. seguir apenas dependências diretas relevantes;
5. ampliar para documentação especializada;
6. usar exploração ampla somente se ainda existir lacuna material.

Não reler arquivo que não mudou durante a mesma tarefa sem motivo.

## 4. Orçamento recomendado

Orçamentos são guardrails, não limites cegos.

- L0/bootstrap: alvo de até ~12 KB.
- Contexto inicial da tarefa: alvo de até ~25 KB além do bootstrap.
- Saída inicial de comando potencialmente grande: até ~6 KB.
- Logs: localizar o erro e ler janela curta ao redor dele antes do log completo.
- Diffs: começar por arquivos da tarefa e ampliar apenas quando houver dependência concreta.

Se uma obrigação crítica exceder o orçamento, **não truncar silenciosamente**. O agente deve ampliar justificadamente o contexto ou dividir a tarefa.

## 5. Classes de relevância

Todo bloco de contexto futuro compilado pelo Idea deve poder ser classificado como:

- `REQUIRED`: necessário para executar ou verificar a tarefa.
- `CONDITIONAL`: necessário apenas quando um gatilho/dependência específica ocorre.
- `DISCOVERY`: útil para investigação, não obrigatório para começar.

Sempre que viável, registrar `reason`/rationale de inclusão.

## 6. Why included / Why excluded

Para pacotes compilados e auditorias de contexto, cada bloco incluído deve ter motivo rastreável.

Exemplos de exclusão legítima:
- fase futura bloqueada;
- documento histórico superseded;
- referência externa apenas inspiracional;
- projeto diferente do projeto atual;
- evidência antiga sem relação com a tarefa.

Excluir não significa apagar: significa não carregar por padrão.

## 7. Delta context e fingerprints

Quando o consumidor já conhece uma revisão de contexto, preferir enviar somente o delta necessário, desde que a integridade possa ser verificada.

Blocos estáveis devem possuir fingerprint/versão quando a implementação futura da F15 suportar isso. Mudança em origem relevante marca contexto derivado como `STALE`.

O Idea não deve prometer caching/tokenização idênticos entre providers. Pode aproveitar cache quando disponível sem acoplar o Core a um provider.

## 8. Saída de comandos e logs

Não despejar árvores completas, XMLs, logs, dumps ou diffs gigantes quando uma busca direcionada responder à pergunta.

Em shells que suportem a operação, preferir uma primeira amostra limitada em bytes, por exemplo:

```sh
COMMAND 2>&1 | head -c 6000
```

No PowerShell, usar seleção/tamanho equivalente. Ampliar somente se o diagnóstico exigir.

## 9. Validação proporcional

Mudança local/pura:
- teste diretamente relacionado.

Mudança de módulo/contrato:
- testes do módulo e validações afetadas.

Mudança transversal, schema, persistência, segurança, export/restore, gate ou release:
- ampliar a validação conforme risco e contrato.

Suite completa, lint completo e build completo pertencem a gates/release ou situações em que o impacto justifica; não são obrigatórios após cada edição documental pequena.

## 10. Contexto do Auditor

Builder e Auditor devem ter isolamento suficiente para que a auditoria continue independente.

O Auditor começa por:
- `AGENTS.md`;
- `PROJECT_STATE.md`;
- audit packet/gate atual;
- diff/evidência da mudança.

`AUDIT.md` entra quando a tarefa é auditoria ou fechamento relevante. O Auditor não precisa carregar toda a história do Builder.

## 11. Métricas futuras do produto

Quando F04/F15 forem implementadas, registrar quando disponível:
- input tokens;
- output tokens;
- cached tokens;
- bytes de contexto;
- blocos incluídos;
- motivo de inclusão;
- estimativa/orçamento e uso real;
- latência;
- provider/model revision quando aplicável.

Métricas principais do Context Compiler:

- **Critical Context Recall:** obrigações críticas incluídas / obrigações críticas necessárias. Meta: 100% nos corpus homologados.
- **Irrelevant Context Rate:** contexto sem relação material / contexto total. Deve ser minimizado depois de garantir recall crítico.
- **Context Compression Ratio:** tamanho/tokens do contexto completo dividido pelo contexto compilado. É métrica, não meta fixa universal.

Nunca otimizar compression sacrificando Critical Context Recall.

## 12. Falhas e overflow

O futuro compilador não deve remover silenciosamente regra obrigatória para caber no teto.

Se o contexto obrigatório não couber:
- marcar `CONTEXT_OVERFLOW` (ou estado equivalente versionado);
- explicar quais blocos são obrigatórios;
- recomendar divisão da tarefa ou orçamento maior.

## 13. Princípio final

**MINIMUM SUFFICIENT CONTEXT, NOT MINIMUM POSSIBLE CONTEXT.**

O objetivo é remover irrelevância, duplicação e repetição — nunca conhecimento material.