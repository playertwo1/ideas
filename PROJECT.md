# PROJECT — Idea

Contrato de visão. Atualize quando o projeto nasce e quando a visão muda; o resto (marcos, estado) fica em `ROADMAP.md` e `STATE.md`. Resumo de `ROADMAP.md` (§1, §8, §9) e `README.md`; o `ROADMAP.md` continua sendo o contrato canônico de produto.

## Problema
Pessoas e agentes começam a escrever código a partir de ideias incompletas e ambíguas: a intenção original se perde, interpretação de IA se mistura com decisão humana e o MVP não tem corte explícito nem critérios verificáveis.

## Para quem
Uso pessoal ou de time pequeno (D01), no Android, para quem quer transformar uma ideia em um pacote de especificação pronto para handoff a pessoas ou agentes.

## MVP — até três fluxos que entregam valor
1. Capturar a ideia preservando a intenção original e esclarecer lacunas com entrevista adaptativa e decisões versionadas (F05–F06).
2. Cortar o MVP e gerar requisitos, fases, dependências e readiness verificáveis (F07–F08).
3. Exportar um pacote Markdown/JSON/ZIP versionado e restaurável, offline (F09).

## Fora do MVP (escrito para não voltar escondido)
- Execução de agentes, Git remoto, criação automática de repositório, pesquisa web automática, grafo spec↔código.
- Colaboração multiusuário, sincronização entre dispositivos, clientes Web/Desktop, backend compartilhado sem necessidade.
- Faturamento, JIRA, Figma, voz, sandbox de código gerado e seleção automática de modelos.
- Idea Factory (F11–F20) antes de `G10 = PASS`.

## Restrições
- Plataforma/ambiente: Android-first no MVP (piloto por APK), Core platform-agnostic (D09).
- Dados sensíveis e privacidade: local-first; ler, editar, salvar, conferir e exportar offline (D03); conteúdo importado é dado, não comando.
- Custos, licenças, integrações obrigatórias: IA provider-agnostic, com fake obrigatório em testes e um provider real homologado no MVP (D02).

## Como saberemos que funcionou
Gate G10 (Go/No-Go) após o piloto F10: pacote útil, compreensível e confiável; round-trip de exportação preserva IDs e relações.

## Autoridade de produto
Product Authority (o usuário/dono do repositório). Builder: Antigravity. Auditor: Codex.

## Alternativas consideradas
Não registrado no repositório (a definir).
