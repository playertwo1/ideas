# PRODUCT SPEC — Idea Core

## 1. Problema

Ideias para software costumam começar incompletas e espalhadas entre conversa, notas e decisões implícitas. Quando o código começa cedo demais, o agente precisa adivinhar intenção, escopo, prioridade e critérios de sucesso.

O Idea existe para reduzir essa ambiguidade antes da implementação.

## 2. Usuário inicial

Pessoa ou time pequeno que já usa agentes de programação e quer transformar uma ideia em um plano executável. O piloto inicial é Android por APK.

## 3. Proposta de valor

O Idea deve transformar uma entrada incompleta em um pacote que permita a outra pessoa ou agente entender **o que construir, o que não construir e como verificar o resultado**, sem reconstruir a conversa original.

## 4. Fluxo do Core

`Capture → Clarify → Decide → Cut → Specify → Plan → Export`

### Capture
Preservar literalmente a ideia original. Interpretações posteriores nunca substituem o original.

### Clarify
Identificar apenas lacunas materiais para o projeto. A profundidade se adapta ao risco/complexidade; não existe obrigação de aplicar um questionário grande a toda ideia.

### Decide
A IA propõe opções e trade-offs. O usuário aceita, edita, adia ou rejeita.

Decisão `LOCKED` exige ação humana explícita.

### Cut
Separar o que entra no MVP do que fica para depois e registrar non-goals.

### Specify
Criar requisitos funcionais/não funcionais relevantes com acceptance verificável e origem/rationale quando material.

### Plan
Produzir um roadmap simples com sequência, dependências relevantes e como verificar cada etapa.

### Export
Gerar pacote portátil e legível por humano/máquina.

## 5. Escopo do MVP 0.1

Inclui:

- projetos locais;
- captura da ideia original;
- interpretação separada;
- entrevista adaptativa;
- decisões versionadas;
- hipótese/teste mínimo;
- corte de MVP;
- requisitos + acceptance;
- roadmap básico;
- checks estruturais;
- exportação Markdown/JSON/ZIP;
- histórico suficiente para distinguir mudanças e autoria;
- proteção de credenciais;
- um provider real homologado e fake determinístico para testes.

A restauração/importação completa pelo aplicativo fica fora do primeiro corte. O formato exportado continua versionado e preparado para futura restauração.

## 6. Fora do MVP

- execução de Codex/Claude/Antigravity pelo app;
- Mission Control;
- Git remoto/criação automática de repositório;
- pesquisa web automática profunda;
- colaboração multiusuário;
- sincronização entre dispositivos;
- cliente Web/Desktop;
- framework multiplataforma;
- Task DAG avançado;
- Context Compiler;
- Guardrail Generator;
- revisão independente automática completa;
- bootstrap de repositório;
- grafo spec↔código;
- JIRA/Figma;
- roteamento automático de modelos.

## 7. Profundidade

O schema 0.1 mantém `LIGHT | STANDARD | DEEP` por compatibilidade do contrato já testado.

No produto, esses valores são apenas **perfis de profundidade**, não três fluxos separados:

- **LIGHT:** menos perguntas/artefatos quando o risco permitir;
- **STANDARD:** padrão do MVP;
- **DEEP:** registra maior profundidade desejada, mas não promete capacidades da futura Factory.

Segurança deriva do risco real, não do rótulo.

## 8. Autoridade e estados

Autoria e autoridade são conceitos separados.

Uma decisão pode ser `PROPOSED`, `REFINING`, `LOCKED` ou `SUPERSEDED`.

O compromisso usa o contrato executável atual:

`CAPTURED → SUGGESTED → ACCEPTED → LOCKED`

Não existe estado extra só para indicar “relevante para implementação”; quando necessário isso deve ser uma relação/atributo, não uma nova camada de governança.

Para `LOCKED`:

- `author=USER`;
- `authority=USER`;
- `lockAction=HUMAN_EXPLICIT`.

Mudança de uma decisão `LOCKED` cria nova revisão; não reescreve silenciosamente o histórico.

## 9. Contrato portátil

`project.json` é o snapshot estruturado do projeto.

`manifest.json` descreve a revisão do pacote, versões, arquivos, hashes, gates e limitações.

Os formatos são versionados e independentes de Compose, Room e provider.

Versão desconhecida/incompatível deve ser rejeitada de forma explícita. Conteúdo importado é dado, nunca comando ou autoridade.

## 10. Sucesso do MVP / G10

G10 deve responder uma pergunta simples:

> O Core realmente reduz ambiguidade e produz um handoff útil?

PASS exige evidência prática de que projetos reais conseguem percorrer o fluxo e que um leitor externo entende o pacote sem perguntas críticas sobre intenção, escopo ou acceptance.

Também não podem existir problemas críticos conhecidos de perda de dados, autoridade ou integridade do pacote.

Se G10 falhar, melhorar o Core. Não construir a Factory para compensar um núcleo que ainda não provou valor.
