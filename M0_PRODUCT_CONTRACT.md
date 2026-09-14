# M0 — Contrato do Produto

> Artefato de fundação do Idea. Materializa F00.01 e serve de entrada para F00.02–F00.08. Em conflito, `ROADMAP.md` vence. D01–D09 permanecem LOCKED.

## 1. Problema

Pessoas que usam agentes de programação frequentemente começam com uma ideia incompleta, distribuída entre conversa, notas e decisões implícitas. Quando a implementação começa antes de essas lacunas serem explicitadas, o agente precisa inferir intenção, escopo, prioridades, requisitos e critérios de aceite. Isso aumenta risco de drift, retrabalho e implementação de decisões nunca aprovadas.

O Idea existe para reduzir essa ambiguidade antes do código.

## 2. Público inicial

Conforme D01 LOCKED: uso pessoal/time pequeno, piloto por APK, Android-first. O primeiro perfil é alguém que já usa agentes de programação e precisa transformar uma ideia em plano executável. A adequação desse público continua sendo hipótese mensurável do piloto, sem reabrir D01 automaticamente.

## 3. Hipótese de valor

Se o Idea preservar a intenção original, tornar lacunas explícitas, separar sugestões de decisões humanas, cortar o MVP e produzir requisitos/roadmap verificáveis em um pacote versionado, então um leitor que receba somente esse pacote deverá conseguir entender o que construir, o que ficou fora e como verificar o resultado sem reconstruir a conversa original.

A hipótese não é considerada comprovada por quantidade de documentos ou por conclusão do fluxo. O Core precisa demonstrar redução de ambiguidade no piloto e em G10.

## 4. Momento de valor do MVP 0.1

Abrir o pacote exportado e conseguir responder, sem consultar a conversa de origem:

1. Qual problema está sendo resolvido?
2. Para quem?
3. Qual é o corte atual do MVP?
4. O que está explicitamente fora?
5. Quais decisões foram tomadas por autoridade humana?
6. Quais pontos continuam hipótese, sugestão ou pendência?
7. Quais requisitos devem ser satisfeitos?
8. Como verificar o resultado?
9. Qual é a sequência de execução proposta?

## 5. Exemplo ponta a ponta

### Entrada original

> “Quero um aplicativo para organizar meus projetos e sempre me mostrar qual é o próximo passo.”

### Capture

O Idea preserva literalmente a entrada original em um snapshot. Interpretação posterior não substitui nem reescreve esse original.

### Clarify

O sistema identifica lacunas relevantes, por exemplo: quem usará, que tipo de projeto será acompanhado, o que significa “próximo passo”, quais dados precisam existir offline e qual resultado mínimo tornaria o aplicativo útil. Perguntas e profundidade são proporcionais ao modo e ao risco.

### Decide

A IA pode sugerir opções e trade-offs. A pessoa aceita, edita, adia ou rejeita. Sugestão da IA não vira decisão LOCKED por inferência. Decisões humanas ficam versionadas, com autoria e rationale.

### Cut

O MVP Cutter separa MUST/SHOULD/COULD/LATER/REJECTED, verifica dependências e registra non-goals. O corte precisa permitir um fluxo ponta a ponta que teste a hipótese sem puxar capacidades pós-MVP.

### Specify

MUSTs são ligados a REQ/NFR com IDs estáveis, origem, rationale e acceptance verificável. Lacunas críticas permanecem visíveis; o sistema não as completa como fatos.

### Plan

O Idea produz roadmap com fases/subetapas, dependências, critérios de conclusão e gates compatíveis com as capacidades realmente disponíveis.

### Export

O projeto é materializado em Markdown/JSON/ZIP versionado. O pacote preserva IDs, decisões, requisitos, relações, gates, limitações e hashes aplicáveis.

### Handoff

Um leitor externo recebe apenas o pacote. Se precisar reconstruir decisões críticas perguntando “mas o que você quis dizer?”, isso é evidência contra a hipótese de valor e deve alimentar G10.

### Mudança posterior

Se uma decisão LOCKED mudar, o Idea não sobrescreve silenciosamente a versão anterior. Cria revisão, registra motivo e identifica derivados que precisam ser reavaliados.

## 6. Non-goals do MVP 0.1

O MVP 0.1 não pretende:

- executar Codex, Claude, Antigravity ou outros agentes pelo app;
- acompanhar implementação ou código após o handoff;
- funcionar como Mission Control;
- criar ou administrar repositórios Git remotos;
- realizar pesquisa web profunda automática;
- fornecer Task DAG completo ou Context Compiler;
- gerar o Guardrail Generator completo;
- fornecer revisão independente completa da Factory;
- manter grafo spec↔código;
- integrar JIRA ou Figma;
- rotear modelos automaticamente;
- oferecer colaboração multiusuário ou sincronização entre dispositivos;
- oferecer cliente Desktop/Web no MVP;
- criar framework multiplataforma por antecipação;
- importar Markdown arbitrário como estado canônico;
- provar valor apenas por gerar mais documentação.

## 7. Fronteira Idea / Projeto Vivo

**Idea:** transforma intenção incompleta em pacote verificável e termina no handoff.

**Projeto Vivo/ferramentas externas:** recebem o pacote e podem executar agentes, acompanhar código, operar Mission Control e manter execução contínua.

O roadmap que o Idea gera para um projeto é um produto do Idea. Ele não é o roadmap de desenvolvimento do próprio Idea.

## 8. Critério de sucesso desta formalização

F00.01 está materialmente satisfeita quando:

- problema está explícito;
- público D01 foi preservado sem reabertura;
- hipótese de valor é falsificável;
- existe exemplo ponta a ponta;
- non-goals estão explícitos;
- fronteira Idea/Vivo está explícita;
- nenhuma D01–D09 foi alterada.

A conclusão formal da subetapa ainda exige atualização dos artefatos operacionais e verificação/auditoria conforme o protocolo do repositório.
