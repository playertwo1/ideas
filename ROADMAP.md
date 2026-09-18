# ROADMAP — Idea

Roadmap operacional enxuto. Detalhamos apenas o caminho até o MVP 0.1. Capacidades pós-MVP permanecem como backlog até o Core provar valor.

## Fundação — concluída

A fundação documental anterior (F00/F01) definiu:

- objetivo e limites do Idea;
- autoridade humana sobre decisões;
- schemas e validação semântica;
- arquitetura Android-first com domínio independente da plataforma;
- contrato portátil `project.json` + `manifest.json`;
- baseline Android inicial.

Os documentos detalhados antigos foram retirados do contexto ativo. O histórico Git continua disponível.

---

## B1 — App base Android

**Objetivo:** primeiro APK instalável com navegação e persistência local mínima.

Entregar:

- projeto Android e módulos mínimos;
- Compose/Material 3;
- Home + criação/abertura de projeto;
- persistência local e rascunho;
- execução dos contratos/validações de domínio existentes;
- build/test/lint reproduzíveis.

**Acceptance:**

- checkout limpo gera APK debug;
- app abre em API 26 e em aparelho alvo;
- criar, salvar, fechar e reabrir um projeto preserva dados;
- falha de leitura não vira projeto vazio;
- `python scripts/check.py` continua passando.

---

## B2 — Captura + IA

**Objetivo:** capturar uma ideia e gerar uma interpretação separada sem corromper o original.

Entregar:

- entrada textual;
- snapshot original preservado;
- interpretação editável;
- `AiProvider` + fake determinístico;
- um provider real homologado;
- armazenamento seguro de credencial;
- timeout/cancelamento/erros básicos.

**Acceptance:**

- editar interpretação não altera o original;
- resposta inválida/obsoleta não é aplicada;
- app continua útil para editar/salvar sem rede;
- credencial não aparece em log/export.

---

## B3 — Clarificar + decidir

**Objetivo:** resolver lacunas materiais e registrar decisões humanas.

Entregar:

- perguntas adaptativas;
- opções/trade-offs quando úteis;
- aceitar, editar, adiar e rejeitar sugestões;
- revisões de decisão;
- ação humana explícita para `LOCKED`;
- histórico legível.

**Acceptance:**

- IA não consegue criar `LOCKED`;
- contradição importante fica visível;
- mudança de decisão preserva revisão anterior;
- perguntas já resolvidas não se repetem sem motivo.

---

## B4 — Cortar + especificar + planejar

**Objetivo:** transformar decisões em um MVP implementável.

Entregar:

- hipótese/teste mínimo;
- corte de escopo e non-goals;
- requisitos + acceptance;
- roadmap básico;
- checks de IDs, referências, cobertura e dependências essenciais.

**Acceptance:**

- todo item essencial do MVP tem requisito/acceptance;
- não existe dependência silenciosa em item fora do MVP;
- roadmap cobre o MVP e cada etapa diz como verificar;
- pendências críticas continuam visíveis.

---

## B5 — Exportar

**Objetivo:** produzir um handoff portátil.

Entregar:

- Markdown legível;
- `project.json`;
- `manifest.json`;
- ZIP;
- hashes/integridade;
- preview e escolha de destino.

**Acceptance:**

- todos os arquivos representam a mesma revisão;
- exportação não inclui credenciais;
- pacote inválido não é apresentado como pronto;
- um leitor externo entende problema, escopo, decisões, requisitos e roadmap.

Restauração completa pelo app não bloqueia o MVP 0.1. O formato continua versionado para permitir isso depois.

---

## B6 — Piloto + estabilização

**Objetivo:** provar valor antes de expandir.

Executar o fluxo completo em alguns projetos reais de complexidade diferente e registrar:

- onde o usuário travou;
- perguntas desnecessárias;
- decisões ambíguas;
- retrabalho;
- dúvidas do leitor no handoff;
- problemas de persistência/exportação.

Corrigir apenas problemas que afetam o Core e repetir os checks impactados.

### G10 — gate de valor

G10 é o único gate estratégico obrigatório antes da expansão.

**PASS** quando houver evidência de que:

- o fluxo ponta a ponta funciona de forma confiável;
- não há problema crítico conhecido de dados/autoridade/integridade;
- o pacote permite a um leitor externo entender o que construir, o que ficou fora e como verificar sem reconstruir a conversa.

**FAIL/NOT_RUN:** manter a Factory bloqueada e melhorar B2–B5.

---

# Pós-MVP — Idea Factory (backlog bloqueado até G10 PASS)

Depois de G10, reavaliar e detalhar somente as capacidades que continuarem úteis:

- pesquisa assistida com fontes;
- validação avançada de hipóteses;
- blueprints técnicos;
- Task DAG;
- Context Compiler;
- geração de guardrails;
- revisão independente automatizada;
- análise avançada de impacto;
- bootstrap de repositório;
- handoff avançado para agentes.

Não existem subfases obrigatórias agora. Cada item só ganha especificação detalhada quando houver necessidade comprovada.

## Regra do roadmap

**Detalhar o próximo problema real, não dez problemas futuros.**
