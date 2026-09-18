# ARCHITECTURE — Idea Core

Arquitetura mínima para o MVP. O objetivo é manter regras de produto testáveis sem criar abstrações antecipadas.

## Princípio

Android é o primeiro cliente. O domínio não deve depender diretamente de Android, Compose, Room, sistema de arquivos ou SDK de provider.

Isso preserva testabilidade e portabilidade do pacote sem obrigar KMP, backend ou segundo cliente.

## Estrutura inicial

Começar com poucos módulos físicos:

- **domain** — modelos, invariantes, validações, casos de uso e portas;
- **data** — persistência, IA, rede e credenciais;
- **export** — materialização determinística do pacote;
- **app** — Compose, navegação e composição das dependências.

Não criar novos módulos enquanto não existir fronteira real que justifique o custo.

## Portas essenciais

### ProjectStore
Carrega projeto/rascunho e salva revisões de forma consistente. Falha de leitura nunca vira projeto vazio silenciosamente.

### AiProvider
Recebe contexto mínimo + revisão de entrada e devolve resposta estruturada. O Core valida a resposta antes de aplicar qualquer coisa.

A IA nunca define IDs definitivos, `LOCKED`, autorização ou gate `PASS`.

### ProjectExporter
Produz Markdown, `project.json`, `manifest.json` e hashes a partir de uma única revisão confirmada.

A UI não monta o formato portátil por conta própria.

### CredentialStore
Guarda credencial fora do pacote exportável e dos logs. A implementação concreta pertence à camada Android/data.

## Persistência

Room é a opção planejada para o MVP Android.

Revisões confirmadas e rascunhos devem ser distinguíveis. Operações que alteram revisão + histórico relacionado precisam ser atômicas quando a consistência exigir.

## Falhas

- **Persistência:** retornar erro explícito; nunca substituir corrupção/falha por estado vazio.
- **IA:** offline, timeout, cancelamento, 401/403/429/5xx ou resposta inválida não alteram conteúdo aceito.
- **Resultado obsoleto:** resposta referente a revisão antiga não sobrescreve revisão atual.
- **Exportação:** falha não modifica o projeto; retry não cria mutação duplicada.

## Baseline Android inicial

Baseline definida antes do código e preservada nesta consolidação:

- minSdk: 26;
- compileSdk: 36;
- targetSdk: 36;
- Android Gradle Plugin: 9.4.0;
- Gradle Wrapper: 9.6.0;
- JDK: 17;
- Kotlin: 2.4.20;
- Compose BOM: 2026.08.00.

No início de B1, confirme compatibilidade da baseline antes de atualizar versões. Não use ranges dinâmicos.

Checks mínimos quando o projeto Android existir:

```text
./gradlew clean assembleDebug
./gradlew test
./gradlew lint
```

Testes instrumentados entram conforme os fluxos Android existirem.

## Dispositivos

Cobertura mínima útil:

- emulador API 26 para compatibilidade mínima;
- aparelho físico Android 16/API 36 para jornada real;
- dados sintéticos das fixtures existentes.

Não transformar uma matriz de dispositivos extensa em bloqueio antes de existir funcionalidade correspondente.

## Contrato portátil

`project.json`, `manifest.json` e `schemas/` são fronteiras estáveis do Core.

Uma futura implementação em outra plataforma pode interpretar o pacote sem conhecer a UI Android que o criou. Isso não autoriza construir outra plataforma agora.

## Regra anti-overengineering

Prefira implementação concreta simples atrás dessas quatro fronteiras. Só extraia novas abstrações quando existir:

- segunda implementação real;
- necessidade clara de teste;
- dependência que precisa ser isolada;
- regra de domínio que seria contaminada pela plataforma.
