# F01.07 — ADR de portabilidade do Core

- **Estado:** aceito para a arquitetura de F01
- **Data:** 14/09/2026
- **Escopo:** fronteiras do Core; nenhuma implementação de plataforma ou provider

## Contexto

O MVP 0.1 é Android-first, mas D09 exige que regras e contratos centrais sejam
portáveis. A arquitetura de F01.04 já separa `domain`, `data`, `export` e `app`;
este ADR torna verificável a fronteira entre o Core e capacidades externas.

## Decisão

O `domain` contém modelos, invariantes, validações, casos de uso e portas. Ele
depende somente da biblioteca padrão e dos contratos estruturados do projeto.
Android, UI, banco, sistema de arquivos, cofre e SDK de IA ficam em adapters e
são conectados pela raiz de composição.

As dependências apontam para o Core:

```text
app ───────────────> domain <────────────── data
                                  ^
                                  │
                                export
```

Não serão criados segundo target, framework multiplataforma, backend ou camada
compartilhada antecipada. Uma extração futura exige cliente real ou decisão
explícita posterior ao G10.

## Portas preservadas

| Porta | Capacidade | Contrato independente de plataforma |
|---|---|---|
| `ProjectStore` | Persistência | Carrega snapshots e grava revisões/rascunhos atomicamente por IDs e revisões do domínio. Erro nunca vira ausência ou projeto vazio. |
| `FileAccess` | Arquivos | Lê ou grava conteúdo por referência lógica e bytes/stream do Core. Escolha de destino e permissões pertencem ao adapter. |
| `AiProvider` | IA | Recebe operação, revisão, schema, contexto mínimo e orçamento; devolve resultado estruturado ou falha tipada sem conceder autoridade ao modelo. |
| `SecretStore` | Segurança | Guarda, resolve e remove segredo por referência opaca. Credencial bruta não integra modelo, log ou pacote exportável. |

Cancelamento, indisponibilidade, conflito, permissão negada, corrupção e falha
remota são resultados explícitos. As portas não expõem `Context`, `Uri`, DAO,
entidade Room, `Parcelable`, seletor Android ou tipos de SDK de provider.

## Adapters previstos, não implementados

| Porta | Adapter futuro | Fase de implementação |
|---|---|---|
| `ProjectStore` | persistência local | F03 |
| `FileAccess` | armazenamento privado e seletor de destino | F03/F09 |
| `AiProvider` | fake determinístico e provider homologado | F04 |
| `SecretStore` | cofre cifrado da plataforma | F04 |

Esses adapters podem ser substituídos sem alterar entidades, validações, casos
de uso, IDs, estados, gates, schemas ou formatos de importação/exportação.

## Verificação obrigatória

Quando os módulos forem criados em F02, a verificação arquitetural deve provar:

1. `domain` compila e seus testes executam sem plugin ou SDK Android;
2. imports `android.*`, Compose, Room e namespaces de SDKs de provider falham no
   teste de arquitetura do Core;
3. testes de contrato exercitam as quatro portas com fakes em memória;
4. UI e adapters não redefinem regras centrais;
5. `project.json`, `manifest.json` e schemas permanecem interpretáveis sem
   classes de UI, Room ou provider.

Nesta fase, a inspeção dos artefatos confirma a regra; não existe código Android
para compilar. O teste automatizado será criado junto ao skeleton autorizado em
F02, antes de qualquer regra central depender da plataforma.

## Consequências

- O MVP pode usar Android sem tornar Android parte do domínio.
- Custódia de credenciais e acesso a arquivos ficam substituíveis e testáveis.
- Há pequeno custo de tradução entre tipos externos e tipos do Core.
- Chamadas diretas de casos de uso a Room, sistema de arquivos ou SDK remoto são
  violações arquiteturais.

## Alternativas rejeitadas

- Tipos Android no Core: contrariam D09 e dificultam testes isolados.
- SDK de provider chamado pelo domínio: acopla regras a fornecedor e transporte.
- Framework multiplataforma agora: amplia o MVP sem um segundo cliente real.

## Aceitação de F01.07

- Interfaces para persistência, arquivos, IA e segurança estão explícitas.
- Nenhuma interface exige Android ou provider específico.
- A regra de dependência e sua futura verificação automatizada estão definidas.
- D01–D09 permanecem `LOCKED`; `G10 = NOT_RUN`; Factory permanece bloqueada.
- F01.08, Android e integração com provider não foram iniciados.
