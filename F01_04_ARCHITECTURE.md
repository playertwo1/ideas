# F01.04 — Arquitetura, módulos e portas

Arquitetura proposta para o Idea Core, alinhada ao `ROADMAP.md` v0.6 e às
decisões D01–D09. Este documento define fronteiras e contratos; não cria código,
módulos Gradle, implementação Android ou integração com provider.

## Objetivos arquiteturais

- Manter regras de domínio independentes de Android, UI, banco e SDK remoto.
- Operar leitura, edição, gravação, conferência e exportação sem rede.
- Trocar mecanismos de persistência, IA e exportação sem alterar regras centrais.
- Preservar `project.json`, `manifest.json` e schemas como contratos portáteis.
- Começar com poucos módulos físicos e evitar abstração multiplataforma prematura.

## Visão de dependências

```text
┌──────────────────────── app ────────────────────────┐
│ apresentação futura · estado de tela · composição  │
└──────────────────────────┬──────────────────────────┘
                           v
┌─────────────────────── domain ──────────────────────┐
│ entidades e regras · casos de uso · portas         │
└───────────────┬──────────────────────┬──────────────┘
                ^                      ^
                │ implementa portas    │ implementa portas
┌───────────────┴──── data ────────────┴──┐   ┌──── export ────┐
│ persistência · IA · credenciais · rede │   │ render · pacote │
└─────────────────────────────────────────┘   └─────────────────┘
```

A direção de dependência aponta para `domain`. O Core conhece contratos, nunca
classes de Android, Room, Compose, sistema de arquivos ou SDK de provider.

## Módulos físicos propostos

| Módulo | Responsabilidade | Pode depender de | Não pode depender de |
|---|---|---|---|
| `domain` | Modelos, invariantes, validações, casos de uso e portas do Core. | Biblioteca padrão e contratos estruturados do projeto. | Android, Compose, Room, SDK remoto, seletor de arquivos ou implementação de storage. |
| `data` | Implementações futuras de persistência, IA, rede e custódia de credenciais. | `domain` e bibliotecas concretas escolhidas nas fases autorizadas. | `app`; regras de produto duplicadas; formatos de UI. |
| `export` | Materialização determinística de uma revisão e montagem futura do pacote portátil. | `domain`, schemas e formatos canônicos. | UI, banco concreto, provider de IA ou classes Android. |
| `app` | Apresentação futura, estado de tela, navegação e raiz de composição. | `domain`, `data` e `export`, conectados na composição. | Regras centrais exclusivas, acesso direto ao banco ou SDK remoto pela apresentação. |

`application`, `model`, `validation` e `ports` são fronteiras internas do módulo
`domain`, organizadas inicialmente por pacotes. Novos módulos físicos exigem uma
fronteira comprovada ou necessidade de build; não são criados nesta subetapa.

## Fronteiras internas do Core

| Fronteira | Responsabilidade | Exemplos de entrada/saída |
|---|---|---|
| `model` | Estado estruturado, IDs, autoria, revisões, decisões, requisitos, gates e snapshots. | Entidades e valores independentes de serialização/plataforma. |
| `validation` | Invariantes determinísticas e códigos de erro do contrato. | Estado candidato → lista exata de violações. |
| `application` | Orquestrar casos de uso e controlar quando resultados podem ser aplicados. | Comando + revisão esperada → resultado ou falha de domínio. |
| `ports` | Descrever capacidades externas necessárias sem escolher tecnologia. | Persistência, IA e exportação definidas abaixo. |

## Porta de persistência

### `ProjectStore`

Responsabilidade: fornecer ao Core snapshots e gravações consistentes por
`projectId`, sem revelar banco, DAO, arquivo ou plataforma.

| Operação conceitual | Entrada | Saída/garantia |
|---|---|---|
| `loadProject` | `projectId` | Snapshot confirmado ou ausência explícita; nunca projeto vazio em erro. |
| `saveRevision` | estado candidato, revisão esperada e eventos | Nova revisão confirmada; alteração, revisão e eventos são atômicos. |
| `saveDraft` | `projectId`, contexto de edição e conteúdo pendente | Identidade do rascunho confirmado, sem promovê-lo a revisão aceita. |
| `loadDraft` | `projectId` e contexto | Rascunho recuperável ou ausência explícita, isolado por projeto. |
| `discardDraft` | identidade exata do rascunho | Confirma descarte sem alterar a última revisão confirmada. |

Conflito de revisão, falta de espaço, corrupção e indisponibilidade são falhas
tipadas. Nenhuma falha pode ser convertida silenciosamente em sucesso ou vazio.

## Porta de IA

### `AiProvider`

Responsabilidade: executar uma operação estruturada sem atribuir autoridade ao
modelo nem expor detalhes do fornecedor ao Core.

**Entrada mínima:** `operation`, `projectId`, `inputRevision`, `schemaVersion`,
dados pertinentes, decisões vigentes, orçamento e `requestId`.

**Saída mínima:** conteúdo estruturado, referências às entradas, hipóteses
explícitas, metadados de uso disponíveis e identidade da execução.

Regras da fronteira:

- O Core valida estrutura, semântica, revisão e autoridade antes de apresentar
  ou aplicar qualquer resultado.
- Resultado atrasado ou de outra revisão permanece separado e não sobrescreve
  conteúdo aceito.
- O modelo não define `projectId`, IDs definitivos, gate `PASS`, autorização ou
  estado `LOCKED`.
- Timeout, cancelamento, autenticação, limite e falha remota são resultados
  explícitos; o rascunho local permanece intacto.
- Fake determinístico e adapter real implementarão a mesma porta em F04; nenhum
  provider é escolhido ou implementado aqui.

## Porta de exportação

### `ProjectExporter`

Responsabilidade: transformar um snapshot confirmado e identificado em uma
representação portátil, sem consultar UI ou estado mutável durante a geração.

| Operação conceitual | Entrada | Saída/garantia |
|---|---|---|
| `preview` | snapshot, versão de schema e seleção de artefatos | Prévia vinculada à revisão, com pendências visíveis. |
| `buildPackage` | a mesma revisão e configuração validada | Arquivos, `project.json`, `manifest.json` e hashes coerentes entre si. |
| `verifyPackage` | pacote candidato | Resultado de integridade antes da entrega ao destino. |

O exportador produz bytes/arquivos lógicos. A escolha e escrita no destino são
responsabilidade de um adapter externo. Cancelar o destino não altera o projeto,
e exportar não executa agentes nem inicia a Idea Factory.

## Fluxos principais

### Editar e salvar

```text
Presentation → caso de uso → validação de domínio → ProjectStore
                                              ← revisão confirmada ou falha
Presentation ← estado observável do caso de uso
```

### Gerar sugestão por IA

```text
Caso de uso → snapshot mínimo + inputRevision → AiProvider
Caso de uso ← resposta estruturada/falha
            → validação + conferência da revisão
            → sugestão separada ou descarte controlado do resultado atrasado
```

### Exportar

```text
Caso de uso → ProjectStore.loadProject → snapshot imutável identificado
            → ProjectExporter.preview/buildPackage/verifyPackage
            → adapter de destino externo
```

O mesmo snapshot alimenta todos os arquivos do pacote. A UI não monta JSON,
Markdown, manifest ou hashes por conta própria.

## Composição e substituição

A raiz de composição futura, pertencente a `app`, conecta casos de uso às
implementações das portas. Testes do Core podem usar implementações em memória e
fake determinístico sem Android, rede, chave ou banco. Substituir um adapter não
muda entidades, validações, casos de uso ou schemas.

## Falhas e recuperação

| Fronteira | Falha | Resposta arquitetural |
|---|---|---|
| Persistência | conflito, espaço insuficiente ou corrupção | Preservar estado visível; retornar falha tipada; nunca criar vazio silencioso. |
| IA | offline, timeout, cancelamento, resposta inválida ou revisão obsoleta | Manter edição local; não aplicar resultado; permitir retomada controlada. |
| Exportação | renderização, integridade ou escrita de destino | Manter projeto e prévia; identificar etapa falha; nova tentativa não duplica mutação do projeto. |

## Verificação arquitetural de F01.04

- `domain` não depende de Android, Compose, Room, Gradle ou SDK de provider.
- Dependências concretas entram somente em adapters e na raiz de composição.
- Persistência, IA e exportação possuem portas com entradas, saídas, falhas e
  garantias observáveis.
- Edição/salvamento/exportação offline não dependem da porta de IA.
- Exportação parte de uma única revisão identificada e não lê estado de UI.
- Nenhuma interface autoriza IA a atribuir IDs definitivos, `LOCKED` ou gate
  `PASS`.
- O desenho usa apenas `app`, `domain`, `data` e `export` como módulos físicos
  iniciais e não cria target adicional, backend ou abstração multiplataforma.

F01.05, implementação Android, escolha de toolchain e homologação de provider
permanecem fora deste documento.
