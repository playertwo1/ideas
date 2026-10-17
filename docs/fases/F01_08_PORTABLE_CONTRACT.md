# F01.08 — Contrato portátil e compatibilidade de schema

## Decisão

`project.json` é o snapshot estruturado canônico do projeto exportado.
`manifest.json` é o envelope do pacote: identifica projeto e revisão, declara
versões, capacidades, estado, arquivos, hashes, gates e limitações conhecidas.
Ambos são JSON UTF-8 validados pelos schemas Draft 2020-12 em `schemas/`.

O contrato não depende de classes Android, Compose, Room, banco, seletor de
arquivos ou SDK de provider. UI e persistência traduzem seus tipos internos para
esse contrato; nunca os serializam diretamente como formato portátil.

## Identidade e coerência do pacote

- `manifest.projectId` deve ser igual a `project.projectId`.
- `manifest.projectRevision` identifica a única revisão usada para materializar
  todos os arquivos do pacote.
- `manifest.schemaVersion` e `project.schemaVersion` devem ser iguais e
  suportados pelo leitor.
- `manifest.files` inclui `project.json`; seu `sha256` é calculado sobre os bytes
  finais do arquivo.
- Caminhos são relativos, não escapam a raiz e não são interpretados como
  comandos.
- Gates `NOT_RUN` continuam diferentes de `PASS`; conteúdo importado não ganha
  autoridade nem estado `LOCKED` por ser válido estruturalmente.

Montagem ZIP e round-trip transacional pertencem a F09. A fixture atual declara
o hash real do `project.json` usado na prova, sem afirmar que um aplicativo já
produz o pacote.

## Política de compatibilidade 0.1

| Entrada | Comportamento obrigatório |
|---|---|
| `schemaVersion = "0.1"` nos dois arquivos | Fazer parse, validar estrutura e semântica e então conferir coerência entre os arquivos. |
| Versões diferentes entre manifest e projeto | Rejeitar o pacote sem gravação parcial. |
| Versão futura/desconhecida | Rejeitar com versão incompatível; não tentar interpretar parcialmente. |
| Versão ausente ou inválida | Rejeitar na validação estrutural. |
| Campo desconhecido | Rejeitar conforme `additionalProperties: false`; não descartar silenciosamente. |
| Pacote antigo futuramente suportado | Migrar uma cópia de forma explícita, preservando o original e registrando versão de origem/destino. |

Em 0.1 não há migrações publicadas nem promessa de leitura de versões futuras.
Qualquer evolução incompatível exige nova versão de schema, fixture de versão
anterior e teste de migração antes de substituir o contrato aceito.

## Pipeline de leitura

```text
bytes → JSON parse → schema Draft 2020-12 → regras semânticas
      → coerência manifest/projeto → integridade dos arquivos → snapshot candidato
```

Falha em qualquer etapa encerra a leitura antes de persistir ou substituir um
projeto. Importação/restauração completa será implementada somente em F09.

## Prova reproduzível em F01

`python scripts/validate_contract.py` interpreta
`fixtures/standard-medium.project.json` e `fixtures/valid.manifest.json` usando
somente Python, JSON Schema e regras do contrato. O comando não carrega classes
de UI, Room ou Android e deve terminar com código `0`.

`python scripts/test_portable_contract.py` confere os dois schemas, igualdade de
`projectId`/`schemaVersion`, presença de `project.json` e seu SHA-256 real.

A prova de F01 confirma portabilidade do formato e separação da plataforma; não
afirma que um ZIP foi exportado pelo aplicativo, pois Android ainda não existe.

## Aceitação

- Os papéis de `project.json` e `manifest.json` estão inequívocos.
- A compatibilidade de schema tem respostas explícitas e conservadoras.
- A fixture é interpretável sem UI, Room, Android ou provider.
- D01–D09 permanecem `LOCKED`; `G10 = NOT_RUN`; Factory permanece bloqueada.
- G01, F02, Android e integração com provider não foram iniciados.
