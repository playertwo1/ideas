# F03 — backup e falhas

`ProjectBackup` exporta projetos, revisões, rascunhos e eventos em JSON versionado (versão 2), com digest SHA-256 do payload. Escreve em arquivo `.partial` e só substitui o backup anterior após gravação completa. A restauração valida digest, versão, IDs e relações antes de inserir os dados em uma transação; IDs já existentes causam rejeição. O banco ativo não é substituído.

`IdeaDatabase.open` verifica a integridade de um arquivo existente antes de entregá-lo ao Room. Corrupção retorna erro explícito e preserva os bytes originais. Falha de escrita preserva o backup anterior e remove o `.partial`. O cenário de espaço insuficiente é testado por falha de escrita injetada; não foi esgotado o armazenamento físico do emulador.
