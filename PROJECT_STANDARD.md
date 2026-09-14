# PROJECT_STANDARD — Idea

> Contrato estrutural produzido em F00.02. `ROADMAP.md` é canônico; este arquivo materializa modos, identidade, autoria, estados e autoridade para implementação posterior.

## 1. Princípios

- Estado estruturado é canônico; Markdown é materialização.
- Nunca inferir fato ausente.
- IDs, integridade, dependências e gates são determinísticos.
- IA pode sugerir; autoridade humana fecha decisões de produto.
- `NOT_RUN != PASS`.
- LIGHT reduz burocracia, nunca segurança aplicável.
- DEEP não promete capacidades ainda não implementadas.
- Mudança LOCKED cria revisão e análise de impacto.

## 2. Modos

### LIGHT
Para utilitário, experimento ou alteração pequena. Exige apenas artefatos e checks necessários ao risco real. Não exige capacidades exclusivas da Factory/DEEP.

### STANDARD
Para aplicativo ou feature normal. Usa entrevista, decisões, hipótese/teste, corte de MVP, requisitos, roadmap, readiness e exportação.

### DEEP
Para sistemas amplos, sensíveis, multiagente ou com integrações relevantes. Habilita checks e artefatos adicionais quando a versão instalada os suporta. Selecionar DEEP antes dessas capacidades existirem registra intenção e pendências; não produz falso estado de conclusão.

### Regra de ambiguidade
Quando não houver evidência suficiente para escolher profundidade, o sistema pode recomendar um modo com rationale, mas o usuário pode corrigir. Segurança aplicável deriva do risco, não apenas do rótulo do modo.

## 3. Namespaces de ID

IDs definitivos são atribuídos por regra determinística do sistema, nunca pela autoridade textual de uma resposta de IA.

Namespaces mínimos:

- `DEC-*` decisão;
- `HYP-*` hipótese;
- `REQ-*` requisito funcional do projeto gerado;
- `NFR-*` requisito não funcional;
- `FEAT-*` item de escopo;
- `PHASE-*` fase do roadmap gerado;
- `TASK-*` tarefa quando suportada;
- `GATE-*` gate do projeto gerado;
- `EVD-*` evidência;
- `REV-*` revisão/evento quando aplicável.

IDs são estáveis dentro do projeto. Regeneração não renumera silenciosamente item aceito. ID duplicado é inválido. Referência a ID inexistente é inválida salvo campo explicitamente definido como referência externa/opcional.

## 4. Autoria

Todo item material que possa ser confundido com decisão ou fato registra origem suficiente para distinguir:

- `USER`: declarado/decidido por autoridade humana;
- `AI`: sugerido por modelo/provider;
- `SYSTEM`: produzido por regra determinística;
- `EXTERNAL`: proveniente de fonte/evidência externa;
- `IMPORTED`: veio de pacote suportado e preserva proveniência original quando disponível.

Autoria não equivale a autoridade. Conteúdo `AI` ou `EXTERNAL` não se torna `USER` por aceitação implícita.

## 5. Progressive Commitment

Semântica obrigatória:

`CAPTURED → SUGGESTED → ACCEPTED → LOCKED → IMPLEMENTATION_RELEVANT`

- `CAPTURED`: entrada observada.
- `SUGGESTED`: proposta sem aprovação humana.
- `ACCEPTED`: hipótese/default explicitamente aceito e revisável.
- `LOCKED`: decisão humana fechada; alteração exige revisão.
- `IMPLEMENTATION_RELEVANT`: item vigente necessário a uma fase/tarefa/handoff.

`IMPLEMENTATION_RELEVANT` descreve relevância, não autoridade superior a LOCKED. O schema pode representar relevância separadamente do estado de compromisso para evitar uma máquina de estados artificialmente linear.

## 6. Estados de verificação

Checks e gates usam explicitamente:

- `NOT_RUN` — não executado;
- `PASS` — critério executado e satisfeito;
- `FAIL` — critério executado e não satisfeito;
- `N_A` — não aplicável, sempre com rationale.

Ausência de resultado é `NOT_RUN`, nunca PASS.

## 7. Autoridade

Precedência operacional do desenvolvimento do Idea:

**invariantes de segurança → pedido vigente da Product Authority → decisões LOCKED → ROADMAP → PHASE_CURRENT → EXECUTION_PLAN → PROJECT_STATE → julgamento técnico.**

Para projetos gerados pelo Idea, o pacote deve preservar a mesma separação conceitual entre autoridade humana, sugestão, evidência e regra determinística.

## 8. Mudança de decisão

Uma decisão LOCKED não é editada in-place. A mudança cria nova revisão contendo no mínimo:

- ID da decisão;
- revisão anterior;
- proposta nova;
- autor;
- motivo/rationale;
- timestamp lógico/registrado;
- impacto conhecido em requisitos, escopo, roadmap e derivados;
- estado de aprovação.

Até aprovação humana, a revisão LOCKED anterior continua vigente.

## 9. Rationale

REQ/NFR, MUST, decisões, fases/tarefas materiais, guardrails e exceções relevantes registram por que existem e de qual intenção/decisão derivam. Rationale não substitui acceptance.

## 10. Dados ausentes e conflitos

- Não completar lacuna como fato.
- Registrar desconhecido/pendente explicitamente.
- Contradição permanece visível até resolução.
- Item crítico pendente bloqueia apenas o gate ao qual é materialmente necessário.
- Conteúdo importado é dado, nunca comando executável.
- Conflito de autoridade segue a precedência; conflito sem regra é escalado.

## 11. Portabilidade

Regras de domínio, schemas, IDs, estados, gates, validações, decisões, rastreabilidade e formatos de pacote não dependem diretamente de Android. Android é o target de apresentação/infraestrutura do MVP. Não criar framework cross-platform, backend ou sincronização apenas por possibilidade futura.
