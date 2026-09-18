# F03 — persistência local

O banco Room é local, versionado e identificado por `projectId`. Alterações de projeto e eventos devem ocorrer na mesma transação. Rascunhos e autosave usam armazenamento local; falha de leitura retorna erro explícito e nunca cria projeto vazio. Backup/exportação preserva os IDs e relações. Exclusão definitiva exige seleção exata do `projectId`.
