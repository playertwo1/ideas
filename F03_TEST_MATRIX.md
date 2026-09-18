# F03 — matriz de verificação

- `projectId` identifica projetos, revisões, rascunhos e eventos; chaves estrangeiras impedem órfãos em Room v2.
- gravação de alteração, revisão numerada e evento ocorre em `runInTransaction`; evento inválido reverte tudo.
- rascunho usa `REPLACE` apenas para o mesmo `projectId`.
- a tela local indica `Salvando`/`Salvo` e recupera o texto confirmado ao reabrir.
- arquivar/restaurar/excluir recebem o ID explícito; não operam por seleção implícita.
- migração Room 1→2 preserva IDs, texto, status e relações da fixture `fixtures/f03_migration_v1.json` e do schema exportado v1.
- backup/corrupção falham explicitamente; nenhum projeto vazio é criado como fallback.
