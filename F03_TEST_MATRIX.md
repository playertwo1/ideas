# F03 — matriz de verificação

- `projectId` é a chave de todas as entidades persistidas e eventos.
- gravação de revisão e evento ocorre em `runInTransaction`.
- rascunho usa `REPLACE` apenas para o mesmo `projectId`.
- arquivar/restaurar/excluir recebem o ID explícito; não operam por seleção implícita.
- migração preserva IDs, texto, status e relações da fixture `fixtures/f03_migration_v1.json`.
- backup/corrupção devem falhar explicitamente; nenhum projeto vazio é criado como fallback.
