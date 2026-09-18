# F03 — backup e falhas

Backup é uma cópia identificada do banco e do schema atual. A restauração valida versão e integridade antes de substituir o arquivo ativo. Erro de leitura, corrupção ou espaço insuficiente retorna erro explícito e preserva o arquivo original; nunca cria um projeto vazio silenciosamente.
