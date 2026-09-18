# IDEA — EXECUTION PLAN

> **Papel deste arquivo:** checklist operacional derivado do `ROADMAP.md` v0.6. Ele não cria regras novas. Em caso de conflito, o `ROADMAP.md` vence.

**Uso diário:** marque apenas trabalho realmente concluído e verificado. O agente executa a fase indicada em `PROJECT_STATE.md` usando `PHASE_CURRENT.md`.

## Regras rápidas

- Não pular fases. Builder reúne evidências; Auditor emite PASS/FAIL da auditoria; Product Authority registra o gate de desenvolvimento que autoriza a troca de fase.
- `PASS` exige a verificação indicada; `NOT_RUN` nunca equivale a `PASS`.
- Antigravity = Builder; Codex = Auditor; usuário = Product Authority.
- Autonomia proporcional: executar o já autorizado e escalar apenas decisão nova, risco novo ou mudança material de escopo.
- **G10 é obrigatório:** F11–F20 não entram em implementação enquanto G10 não for `PASS`.
- Atualizar `PROJECT_STATE.md` após cada subetapa/gate concluído.

---

## M0 — Contrato do produto

### [x] F00 — Padrão de projeto e congelamento do MVP

**Resultado:** contrato que permite identificar objetivamente o que falta em um pacote.

- [x] **F00.01 — Formalizar problema, público D01 já LOCKED, hipótese de valor e fronteira Idea/Vivo**
  - Verificar: Um exemplo real percorre o fluxo; non-goals escritos
- [x] **F00.02 — Produzir `PROJECT_STANDARD.md`: modos, IDs, autoria, estados e autoridade**
  - Verificar: LIGHT não exige capacidades DEEP; casos ambíguos têm regra
- [x] **F00.03 — Definir `project.schema.json`, `manifest.schema.json` e schemas de resposta IA**
  - Verificar: pipeline separado; válidos passam; negativas passam estruturalmente e falham semanticamente por códigos exatos
- [x] **F00.04 — Fixar artefatos por versão/modo e catálogo de gates**
  - Verificar: Matriz sem artefato obrigatório impossível na versão
- [x] **F00.05 — Preparar três fixtures: utilitário simples, app médio e projeto sensível**
  - Verificar: Faltas conhecidas são detectadas, não preenchidas como fatos
- [x] **F00.06 — Materializar D01–D09 LOCKED nos ADRs/contratos aplicáveis e congelar corte 0.1**
  - Verificar: Usuário identifica exatamente o que entra e o que fica fora
- [x] **F00.07 — Formalizar Core/Factory, G10 Go/No-Go, Progressive Commitment, rationale e protocolo Builder/Auditor**
  - Verificar: Contratos impedem F11+ sem G10 PASS e distinguem autoridade de produto, execução e auditoria
- [x] **F00.08 — Formalizar D09 e Platform Portability Principle sem adicionar um segundo target ao MVP**
  - Verificar: PROJECT_STANDARD separa Core de adapters e proíbe abstração multiplataforma prematura

**G00**
- [x] Todas as subetapas acima concluídas
- [x] Verificações executadas
- [x] Findings do G00 corrigidos e evidência reproduzível atualizada
- [x] Auditoria proporcional concluída
- [x] Codex/Auditor emitiu `AUDIT RESULT: PASS`
- [x] Product Authority registrou explicitamente `G00 = PASS` — três fixtures auditáveis, contrato versionado e bloqueadores de fundação resolvidos.

### [x] F01 — Experiência e arquitetura da primeira versão

**Resultado:** fluxo navegável revisável e fronteiras técnicas do próprio app.

- [x] **F01.01 — Mapear Home, Nova ideia, Projeto, Entrevista, Decisões, Escopo, Spec, Roadmap e Exportação**
  - Artefato: `F01_01_SCREEN_MAP.md`
  - Verificar: Cada tela tem objetivo, entradas, saídas, ação principal e transições
- [x] **F01.02 — Desenhar wireframes do fluxo principal e estados vazio/erro/offline/interrompido**
  - Artefato: `F01_02_WIREFRAMES.md`
  - Verificar: Usuário encontra retomar, editar e exportar sem explicação técnica
- [x] **F01.03 — Definir navegação, salvar/voltar e recuperação de rascunho**
  - Artefato: `F01_03_NAVIGATION_AND_DRAFTS.md`
  - Verificar: Navegar não descarta entrada sem aviso
- [x] **F01.04 — Registrar arquitetura, módulos propostos e portas de IA/export/persistência**
  - Artefato: `F01_04_ARCHITECTURE.md`
  - Verificar: Domínio não depende de Android nem SDK remoto
- [x] **F01.05 — Fixar aparelhos de teste, minSdk e toolchain compatível pela documentação vigente**
  - Artefato: `F01_05_TARGET_ENVIRONMENT.md`
  - Verificar: Ambiente e comandos do build de exemplo são reproduzíveis; execução aguarda fase que autorize Android
- [x] **F01.06 — Desenhar autorização de LOCK e troca de decisão com impacto**
  - Artefato: `F01_06_LOCK_AUTHORITY_PROTOTYPE.md`
  - Verificar: Protótipo distingue sugestão, hipótese aceita e decisão fechada
- [x] **F01.07 — Registrar ADR de portabilidade: Core independente de Android; interfaces para persistência, arquivos, IA e segurança**
  - Artefato: `F01_07_PORTABILITY_ADR.md`
  - Verificar: Teste/inspeção arquitetural confirma que regras centrais não importam APIs Android
- [x] **F01.08 — Definir `project.json`/`manifest.json` como contrato portátil e política de compatibilidade de schema**
  - Artefato: `F01_08_PORTABLE_CONTRACT.md`
  - Verificar: Fixture exportada pode ser interpretada sem depender de classes de UI/Room

**G01**
- [x] Todas as subetapas acima concluídas
- [x] Verificações executadas
- [x] Auditoria proporcional concluída
- [x] `G01 = PASS` — registrado pela Product Authority após auditoria independente; fluxo aprovado para implementação.

---

## M1 — Fundação offline

### [ ] F02 — Skeleton Android e ciclo de desenvolvimento *(próxima fase autorizada)*

**Resultado:** aplicativo instalável com navegação base e verificações automatizadas.

- [ ] **F02.01 — Criar projeto, wrapper, catálogo de versões e módulos mínimos**
  - Verificar: Checkout limpo produz APK debug
- [ ] **F02.02 — Tema, tipografia, navegação e tela Home vazia**
  - Verificar: Instalação e navegação em emulador e aparelho
- [ ] **F02.03 — Estabelecer convenções e commands de build/lint/test**
  - Verificar: Um comando documentado reproduz cada check
- [ ] **F02.04 — CI com build, testes de domínio e lint, sem segredos no repo**
  - Verificar: Falha introduzida no fixture falha no job esperado
- [ ] **F02.05 — Configurar erros visíveis e logging com dados sintéticos**
  - Verificar: Relatório não inclui ideia ou credencial real

**G02**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G02 = PASS` — APK abre e os checks passam.

### [ ] F03 — Persistência local e histórico confiável

**Resultado:** projetos e revisões sobrevivem a reinício e falhas.

- [ ] **F03.01 — Implementar entidades atuais, DAOs e relações por projectId**
  - Verificar: Teste impede associação entre projetos distintos
- [ ] **F03.02 — Implementar transação alteração + revisão + evento**
  - Verificar: Falha injetada não deixa meio estado gravado
- [ ] **F03.03 — Rascunhos, autosave, indicador salvo e recuperação**
  - Verificar: Matar processo após salvar preserva dados confirmados
- [ ] **F03.04 — Exportar schema Room e criar teste de migração de fixture**
  - Verificar: Migração preserva IDs, textos e relações
- [ ] **F03.05 — Arquivar, restaurar e excluir definitivamente projeto**
  - Verificar: Ações têm efeito previsível e seleção exata do projeto
- [ ] **F03.06 — Política de backup, armazenamento e erros de espaço/corrupção**
  - Verificar: Erro de leitura não vira projeto vazio silenciosamente

**G03**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G03 = PASS` — teste offline e matriz de interrupção passam; sem fallback destrutivo para dados reais.

---

## M2 — MVP 0.1 / Idea Core

### [ ] F04 — Contrato de IA e execução controlada de geração

**Resultado:** uma geração estruturada pode falhar, cancelar e ser retomada sem corromper projeto.

- [ ] **F04.01 — Implementar porta AiProvider e fake determinístico**
  - Verificar: Casos de uso funcionam sem rede ou chave
- [ ] **F04.02 — Homologar um provider, credencial e formatos de resposta**
  - Verificar: Chamada real mínima autorizada gera payload validável
- [ ] **F04.03 — Cofre cifrado, remover/trocar credencial e consentimento de envio**
  - Verificar: Chave ausente de logs, banco exportável e pacote
- [ ] **F04.04 — Validation pipeline: parse, schema, semântica básica e inputRevision**
  - Verificar: JSON inválido e resultado atrasado não alteram dados aceitos
- [ ] **F04.05 — Cancelamento, timeout, erros 401/403/429/5xx e estados persistidos**
  - Verificar: Cada falha tem mensagem e retomada sem aplicação duplicada
- [ ] **F04.06 — Prompt registry e contabilização por GenerationRun**
  - Verificar: Revisão do prompt, provider e uso ficam identificáveis
- [ ] **F04.07 — Limites de custo/tokens e envio mínimo de contexto**
  - Verificar: Reparos respeitam orçamento; projeto B não aparece no pedido A

**G04**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G04 = PASS` — fixture adversarial completo passa; uma integração real homologada.

### [ ] F05 — Captura e interpretação da ideia

**Resultado:** usuário cria projeto e compara interpretação com original.

- [ ] **F05.01 — Entrada textual com título, limites e salvar rascunho**
  - Verificar: Acentos, multiline e limites preservados
- [ ] **F05.02 — Snapshot original imutável e comparação com resumo**
  - Verificar: Editar resumo não altera original/hash
- [ ] **F05.03 — Tipo de projeto, restrições e modo sugerido com motivo**
  - Verificar: Usuário pode corrigir recomendação e ver efeito
- [ ] **F05.04 — Gerar interpretação como sugestão e permitir editar/aceitar**
  - Verificar: IA não converte detalhe presumido em decisão humana
- [ ] **F05.05 — Tela de projeto com progresso e próxima ação explicável**
  - Verificar: Progresso corresponde aos gates, não só quantidade de telas

**G05**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G05 = PASS` — criar e retomar offline, original preservado.

### [ ] F06 — Entrevista inteligente e decisões versionadas

**Resultado:** preencher lacunas com esforço limitado e decisões persistentes.

- [ ] **F06.01 — Catálogo de lacunas por tipo/modo e criticidade**
  - Verificar: Casos simples não recebem questionário completo DEEP
- [ ] **F06.02 — Rodadas adaptativas com opções, recomendação e trade-offs**
  - Verificar: Perguntas já respondidas não repetem sem causa
- [ ] **F06.03 — Responder, editar, adiar e aceitar default explícito**
  - Verificar: CRÍTICA adiada continua bloqueando gate pertinente
- [ ] **F06.04 — DecisionRevision e transições Propose/Refine/Lock**
  - Verificar: Só ação humana autorizada fecha a revisão
- [ ] **F06.05 — Reabertura com delta, motivo e invalidação de derivados**
  - Verificar: Resultado de IA não desfaz lock nem edição humana
- [ ] **F06.06 — Stop condition, orçamento de rodadas e lista de lacunas**
  - Verificar: Esgotar orçamento gera escolha clara, não aprovação automática
- [ ] **F06.07 — Histórico legível de decisões e autoria**
  - Verificar: Usuário identifica o que decidiu e o que foi assumido

**G06**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G06 = PASS` — nenhuma decisão muda sem rastreabilidade; fixture com contradição fica visível.

### [ ] F07 — Hipótese mínima, MVP Cutter e requisitos

**Resultado:** escopo pequeno ligado a uma hipótese e a critérios verificáveis.

- [ ] **F07.01 — Ficha simples de problema, hipótese, teste barato, métrica e limiar**
  - Verificar: Dados não medidos ficam NOT_TESTED
- [ ] **F07.02 — Features MUST/SHOULD/COULD/LATER/REJECTED, justificativas e dependências**
  - Verificar: MUST não depende silenciosamente de item fora do corte
- [ ] **F07.03 — Definir fluxo ponta a ponta mínimo e non-goals**
  - Verificar: Existe demonstração que testa a hipótese sem pós-MVP
- [ ] **F07.04 — REQ/NFR com IDs estáveis, origem e acceptance editável**
  - Verificar: 100% dos MUST ligados a REQ/NFR; critérios não vazios
- [ ] **F07.05 — Jornadas, estados e erros relevantes ao escopo**
  - Verificar: Caminho de falha material aparece no requisito correspondente
- [ ] **F07.06 — Prévia e aprovação de lote; checks de cobertura e duplicação**
  - Verificar: Regeneração preserva IDs aceitos e marca potenciais duplicatas

**G07**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G07 = PASS` — escopo, hipótese/teste e acceptance aprováveis; zero MUST órfão.

### [ ] F08 — Roadmap básico e readiness do núcleo

**Resultado:** converter o corte em fases e subetapas verificáveis.

- [ ] **F08.01 — Modelo Phase/RoadmapItem com objetivos, refs e ordem/dependências**
  - Verificar: Cada subetapa tem entrega e verify
- [ ] **F08.02 — Geração e edição manual de fases, subetapas e prioridades**
  - Verificar: Reordenar não rompe referências e informa dependência violada
- [ ] **F08.03 — Separar MVP/pós-MVP e mostrar pré-requisitos**
  - Verificar: Etapa MVP não depende de entrega pós-MVP oculta
- [ ] **F08.04 — Checks: IDs, órfãos, cobertura, referências, loops e revisões antigas**
  - Verificar: Fixtures inválidos falham com IDs/localização exatos
- [ ] **F08.05 — Painel de readiness por dimensão e pendências acionáveis**
  - Verificar: Gate não executado aparece como pendente

**G08**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G08 = PASS` — cada MUST aparece em pelo menos um item do roadmap e cada item tem verificação ou justificativa de infraestrutura.

### [ ] F09 — Exportação Markdown ZIP e restauração

**Resultado:** levar o projeto para fora do app com versão, relações e integridade.

- [ ] **F09.01 — Renderizadores determinísticos a partir do snapshot aceito**
  - Verificar: Mesmas entradas/versões geram mesmo conteúdo canônico
- [ ] **F09.02 — Manifest, project.json, hashes e status rascunho/pronto**
  - Verificar: Todos os arquivos pertencem à mesma revisão
- [ ] **F09.03 — Preview, seleção de destino e compartilhar pacote**
  - Verificar: Salvar/cancelar no seletor Android não perde projeto
- [ ] **F09.04 — Restauração do próprio formato em novo projeto/revisão controlada**
  - Verificar: Round-trip preserva IDs internos e relações; colisão é detectada
- [ ] **F09.05 — Proteção contra caminhos relativos maliciosos, ZIP excessivo e schema futuro**
  - Verificar: Pacote inválido não grava parcialmente nem sobrescreve dados
- [ ] **F09.06 — Checks de segredos, Unicode e links internos**
  - Verificar: ZIP abre em ferramenta comum; refs resolvem; sem credenciais

**G09**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G09 = PASS` — round-trip dos três fixtures e leitura externa do pacote aprovados.

### [ ] F10 — Piloto e estabilização do MVP 0.1

**Resultado:** demonstrar utilidade e confiabilidade antes de ampliar o produto.

- [ ] **F10.01 — Rodar 12 casos: 4 simples, 4 médios, 4 ambíguos/sensíveis**
  - Verificar: Nenhum caso é aprovado com lacuna crítica conhecida
- [ ] **F10.02 — Exercitar offline, morte de processo, rede, banco cheio e cancelamento**
  - Verificar: Zero perda de gravação confirmada nos cenários executados
- [ ] **F10.03 — Medir leitura/salvamento/export e usabilidade acessível**
  - Verificar: Metas da seção 9 avaliadas com aparelho e amostra registrados
- [ ] **F10.04 — Piloto com cinco participantes e dois projetos por pessoa**
  - Verificar: Tempo, abandono, correções e dúvidas de handoff registrados
- [ ] **F10.05 — Corrigir bloqueadores, repetir apenas checks afetados e regressão essencial**
  - Verificar: Nenhum defeito crítico/alto aberto
- [ ] **F10.06 — Release notes, limitações e pacote instalável de piloto**
  - Verificar: Versão se anuncia como 0.1, sem prometer DEEP completo

**G10**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G10 = PASS` — confiabilidade obrigatória e resultado mínimo de utilidade medidos. **PASS libera F11–F20; FAIL ou NOT_RUN bloqueia implementação da Idea Factory.**

> **STOP obrigatório:** se `G10 != PASS`, não iniciar F11. Corrigir F06–F08 conforme o ROADMAP.

---

## M3 — Idea Factory: pesquisa e blueprints

### [ ] F11 — Pesquisa assistida com evidências

**Resultado:** fontes externas influenciam decisões de modo rastreável.

- [ ] **F11.01 — Source/Claim e captura manual de link, data, localizador e licença**
  - Verificar: Fato, inferência e recomendação têm tipos diferentes
- [ ] **F11.02 — Adapter de busca/leitura com fontes oficiais priorizadas**
  - Verificar: Falha/limite de acesso é registrada, sem resumo inventado
- [ ] **F11.03 — ResearchRun com cache datado, orçamento e deduplicação**
  - Verificar: Mesma fonte não aparece como evidências independentes
- [ ] **F11.04 — Matriz de alternativas e classificação com justificativa**
  - Verificar: Cada fonte relevante liga a decisão/hipótese ou descarte
- [ ] **F11.05 — Segurança de fetch e conteúdo não confiável**
  - Verificar: URLs privadas, redirect malicioso e injeção não geram ações
- [ ] **F11.06 — Gerar RESEARCH e permitir revisão/correção da citação**
  - Verificar: Pessoa consegue localizar o suporte de cada afirmação

**G11**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G11 = PASS` — afirmações materiais têm fonte ou rótulo de não verificado.

### [ ] F12 — Idea Validator completo

**Resultado:** decidir BUILD/PIVOT/PARK/REJECT com hipótese e evidências.

- [ ] **F12.01 — Problema, público, alternativas e hipótese central**
  - Verificar: Há uma hipótese principal clara, sem mercado inventado
- [ ] **F12.02 — Fatal flaws, dependências externas e experimento barato**
  - Verificar: Cada risco alto tem teste ou mitigação viável
- [ ] **F12.03 — Registrar plano de experimento e resultados observados**
  - Verificar: BUILD com teste planejado não se declara hipótese comprovada
- [ ] **F12.04 — Veredito recomendado, motivo, condições e confirmação humana**
  - Verificar: IA não arquiva/rejeita definitivamente sozinha
- [ ] **F12.05 — PIVOT cria nova revisão; PARK registra condição de retomada**
  - Verificar: Decisões válidas e histórico continuam consultáveis

**G12**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G12 = PASS` — BUILD exige hipótese, teste, métrica/limiar e ausência de impedimento crítico sem resposta.

### [ ] F13 — Geradores de UX arquitetura dados integrações e segurança

**Resultado:** produzir blueprint técnico suficiente para decompor tarefas.

- [ ] **F13.01 — Screen map, jornadas e estados loading/empty/error/success**
  - Verificar: Cada fluxo MUST tem telas e comportamento definidos
- [ ] **F13.02 — Componentes, ownership, interfaces e alternativas arquiteturais**
  - Verificar: Arquitetura cobre os REQs sem serviços sem função
- [ ] **F13.03 — Modelo de dados com relações, invariantes e migração**
  - Verificar: Cada entidade tem dono e fonte da verdade
- [ ] **F13.04 — Contratos de integrações, auth, limites, indisponibilidade e fallback**
  - Verificar: API suposta é marcada para verificação documental
- [ ] **F13.05 — Threat model e riscos LOW/MEDIUM/HIGH/CRITICAL**
  - Verificar: Risco alto/crítico sem mitigação bloqueia handoff
- [ ] **F13.06 — Master Spec curta e specs locais sob demanda**
  - Verificar: Uma regra tem localização canônica e referências
- [ ] **F13.07 — Validar coerência entre UX, dados, arquitetura e escopo**
  - Verificar: Fluxos não fazem referência a recurso excluído sem explicação

**G13**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G13 = PASS` — blueprint aprovado e contratos suficientes para F14.

---

## M4 — Decomposição e contexto

### [ ] F14 — Task DAG e compilador de tarefas

**Resultado:** pacote de tarefas pequenas, ordenáveis e verificáveis.

- [ ] **F14.01 — Schema Task com refs, dependeDe, scope, acceptance, verify e risco**
  - Verificar: Campo obrigatório ausente impede READY
- [ ] **F14.02 — Converter roadmap em tarefas e preservar vínculo ao requisito**
  - Verificar: 100% dos MUST têm tarefa; infra tem justificativa
- [ ] **F14.03 — Algoritmo DAG: ordenação, ciclos, ausências e autorreferência**
  - Verificar: Ciclo mostra caminho completo; ordem válida satisfaz dependências
- [ ] **F14.04 — Warnings de tamanho e divisão sugerida**
  - Verificar: Divisão conserva REQs, acceptance e dependências
- [ ] **F14.05 — Ondas de paralelismo sem sobreposição de escrita**
  - Verificar: Tarefas sem aresta mas com arquivo compartilhado não rodam juntas
- [ ] **F14.06 — Exportar TASKS e relações em formato estruturado**
  - Verificar: Cada tarefa tem verify executável ou roteiro manual objetivo

**G14**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G14 = PASS` — zero ciclos/órfãos; nenhuma tarefa pronta acima do orçamento sem justificativa.

### [ ] F15 — Context Compiler por tarefa

**Resultado:** contexto mínimo suficiente, versionado e íntegro.

- [ ] **F15.01 — Resolver REQ/DEC/spec/interface transitivos por IDs e paths**
  - Verificar: Fixture com dependência indireta inclui o contrato necessário
- [ ] **F15.02 — Ordenar contexto por obrigação e remover duplicação**
  - Verificar: Nenhum requisito/invariante obrigatório é perdido
- [ ] **F15.03 — Aplicar orçamento com tokenizer do adapter ou estimativa rotulada**
  - Verificar: Overflow obrigatório gera bloqueio específico, sem truncamento
- [ ] **F15.04 — Fingerprint, versões e motivo de inclusão por bloco**
  - Verificar: Mudança em origem torna contexto antigo STALE
- [ ] **F15.05 — Exportar TASK-xxx-CONTEXT e índice por tarefa**
  - Verificar: Agente identifica objetivo, limites e como verificar sem ler tudo

**G15**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G15 = PASS` — precisão e cobertura em corpus de tarefas; zero exclusão de regra crítica.

### [ ] F16 — Guardrails e pacote de instruções

**Resultado:** regras proporcionais ao risco e ao trabalho autorizado.

- [ ] **F16.01 — Política canônica de escopo, permissões, aprovações e autonomia proporcional**
  - Verificar: Cada bloqueio tem causa concreta, responsável e condição de saída; trabalho já autorizado não pede aprovação repetida
- [ ] **F16.02 — Gerar AGENTS/WATCHDOG/AUDIT a partir da mesma política canônica por perfil de risco**
  - Verificar: Regras não se contradizem entre arquivos; cada arquivo tem papel explícito (executor/watchdog/auditor)
- [ ] **F16.03 — Respeitar autorização existente e delimitar ações destrutivas/externas**
  - Verificar: Cenário autorizado não pede aprovação repetida
- [ ] **F16.04 — Criar entrada de descoberta na raiz e instruções detalhadas em /ai**
  - Verificar: Agente de destino encontra regras no teste de handoff
- [ ] **F16.05 — Testes de injeção em conteúdo, paths e documentos**
  - Verificar: Conteúdo de fonte não altera autoridade de políticas

**G16**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G16 = PASS` — guardrails pequenos e acionáveis; nenhuma autorização ampliada pelo modelo.

---

## M5 — Revisão e consistência

### [ ] F17 — Revisão independente do pacote

**Resultado:** achados reproduzíveis sobre a revisão candidata.

- [ ] **F17.01 — Criar contrato de evaluator com snapshot e rubrica**
  - Verificar: Não recebe histórico/raciocínio privado do gerador
- [ ] **F17.02 — Revisar cobertura, contradições, scope e verificabilidade**
  - Verificar: Achados citam IDs, evidência e condição de resolução
- [ ] **F17.03 — Classificar severidade e permitir contestação fundamentada**
  - Verificar: Revisão humana não precisa aceitar achado falso
- [ ] **F17.04 — Ciclo de correção e reavaliação de itens impactados**
  - Verificar: Resolvido exige evidência; edição não apaga achado
- [ ] **F17.05 — Limite inicial de duas rodadas automáticas**
  - Verificar: Persistência de bloqueio gera decisão explícita, não loop infinito

**G17**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G17 = PASS` — zero CRITICAL/HIGH aberto na revisão candidata e aprovação humana vinculada ao snapshot.

### [ ] F18 — Consistência semântica mudança e housekeeping

**Resultado:** evoluir projetos sem contradições escondidas nem documentos obsoletos ativos.

- [ ] **F18.01 — Grafo de impacto entre decisões, requisitos, artefatos e tarefas**
  - Verificar: Fixture de mudança alcança todos os derivados esperados
- [ ] **F18.02 — Comparação semântica assistida com evidência**
  - Verificar: Contradição sugerida não vira exclusão automática
- [ ] **F18.03 — Replanejar após LOCK alterado e invalidar aprovações impactadas**
  - Verificar: Pacote aprovado antigo não aparece atualizado sem revisão
- [ ] **F18.04 — Arquivar artefatos substituídos e limpar temporários**
  - Verificar: Fonte vigente permanece única; histórico recuperável
- [ ] **F18.05 — Incrementalidade, cache e regressão por fingerprint**
  - Verificar: Mudança local não obriga gerar o projeto inteiro

**G18**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G18 = PASS` — zero contradição crítica conhecida, órfão ou artefato stale no pacote pronto.

---

## M6 — Bootstrap controlado

### [ ] F19 — Repository Bootstrap controlado

**Resultado:** pacote aprovado vira projeto inicial reprodutível em perfil homologado.

- [ ] **F19.01 — Perfil genérico de docs e perfil Android com toolchain fixada**
  - Verificar: Template tem origem, versão e teste em ambiente limpo
- [ ] **F19.02 — Preview completo dos arquivos e destino**
  - Verificar: Nada é sobrescrito fora do escopo aprovado
- [ ] **F19.03 — Gerar README, gitignore, docs/specs/ai/tasks e CI do perfil**
  - Verificar: Pacote passa checks determinísticos e scanner de segredos
- [ ] **F19.04 — Handoff para bootstrap em desktop/runner homologado**
  - Verificar: Android não depende de executar Gradle/Git localmente no telefone
- [ ] **F19.05 — Opção de GitHub remoto com autorização e visibilidade explícitas**
  - Verificar: Retry não cria repositório/commit duplicado
- [ ] **F19.06 — Manifest de bootstrap e instruções de recuperação**
  - Verificar: Mesma entrada produz mesma árvore de arquivos homologada

**G19**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G19 = PASS` — build/check mínimo do perfil passa e a documentação é versionada.

---

## M7 — Dogfood e 1.0

### [ ] F20 — Dogfood com Projeto Vivo e release 1.0

**Resultado:** comprovar a fábrica em um projeto real.

- [ ] **F20.01 — Inventariar documentos e importar texto/Markdown ou conteúdo textual de DOCX, preservando tabelas/links e origem**
  - Verificar: Decisões anteriores não viram automaticamente fatos aprovados; imagem/PDF exige transcrição revisada fora deste importador
- [ ] **F20.02 — Mapear correspondências e preservar decisões válidas confirmadas**
  - Verificar: Identidade/justificativa preservadas; conflitos apresentados
- [ ] **F20.03 — Executar DEEP apenas nas lacunas necessárias**
  - Verificar: Perguntas já resolvidas não reiniciam o processo
- [ ] **F20.04 — Gerar, revisar e aprovar pacote completo**
  - Verificar: Todos os gates 1.0 passam no snapshot final
- [ ] **F20.05 — Entregar pacote a agente externo e observar primeira fatia delimitada**
  - Verificar: Agente identifica contexto, escopo e verify sem pergunta crítica
- [ ] **F20.06 — Registrar desvios do handoff e melhorar a fábrica**
  - Verificar: Correções viram regressões úteis do gerador
- [ ] **F20.07 — Homologar 1.0, compatibilidade, backup e notas de migração**
  - Verificar: Usuário consegue retomar projeto vindo de 0.1

**G20**
- [ ] Todas as subetapas acima concluídas
- [ ] Verificações executadas
- [ ] Auditoria proporcional concluída
- [ ] `G20 = PASS` — DoD 1.0 atendida e primeiro handoff real validado.

---

## Encerramento 1.0

- [ ] G20 = PASS
- [ ] DoD 1.0 atendida
- [ ] Primeiro handoff real validado
- [ ] Compatibilidade/backup/migração documentados
