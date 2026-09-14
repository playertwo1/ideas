# F01.05 — Ambiente alvo e matriz de dispositivos

Baseline documental fixada em 14/09/2026 para orientar F02 e as medições futuras
do MVP 0.1. Nenhum projeto, build, módulo ou código Android é criado aqui.

## Baseline de SDK

| Parâmetro | Valor fixado | Rationale |
|---|---:|---|
| `minSdk` | 26 | Android 8.0 é a menor plataforma suportada; a compatibilidade real será exercitada no AVD mínimo. |
| `compileSdk` | 36 | Android 16 é a plataforma estável adotada; não compilar contra preview. |
| `targetSdk` | 36 | O piloto será desenhado e testado com os comportamentos estáveis do Android 16. |
| Build Tools | 36.0.0 | Versão padrão documentada para AGP 9.4.0. |
| NDK | Não aplicável | O escopo atual não requer código nativo. |

Android 17/API 37 não integra a baseline porque a documentação consultada ainda
o apresenta como beta. Compatibilidade futura pode ser testada separadamente,
sem elevar preview a requisito de build ou alterar `targetSdk` silenciosamente.

## Toolchain fixada

| Ferramenta | Versão | Regra |
|---|---:|---|
| Android Studio | Quail 4 `2026.1.4` | Versão estável de referência. |
| Android Gradle Plugin | `9.4.0` | Versão estável correspondente ao Studio escolhido. |
| Gradle Wrapper | `9.6.0` | Versão mínima e padrão documentada para AGP 9.4.0. |
| JDK | `17` | Versão mínima e padrão documentada para AGP 9.4.0. |
| Kotlin | `2.4.20` | Release estável vigente; compatível com AGP 8.5.2 ou superior. |
| Compose BOM | `2026.08.00` | BOM estável vigente; versões individuais não serão fixadas fora do BOM sem justificativa. |
| Android Emulator | `37.1.11` | Versão estável vigente na data da decisão. |

Versões são exatas, sem `+`, ranges ou resolução dinâmica. Uma atualização exige
novo registro de compatibilidade e repetição dos checks afetados.

## Dispositivos obrigatórios

### AVD mínimo

| Campo | Valor |
|---|---|
| Perfil | Pixel phone padrão, tela compacta/normal |
| System image | Android 8.0, API 26, x86_64 |
| RAM | 4 GB |
| Origem da imagem | AOSP ou Google APIs estável; registrar qual foi instalada |
| Finalidade | Instalação mínima, fluxo principal, offline, persistência, exportação e acessibilidade básica |

O AVD deve executar exatamente API 26 porque a documentação oficial recomenda
testar integralmente no nível declarado em `minSdk`.

### Aparelho físico intermediário

| Campo | Valor |
|---|---|
| Modelo de referência | Google Pixel 9a |
| RAM | 8 GB |
| Sistema alvo | Android 16, API 36, estável |
| Armazenamento | Variante de 128 GB ou 256 GB; registrar a usada |
| Finalidade | Fluxo real, desempenho, interrupção, armazenamento, modo avião e acessibilidade |

Antes de qualquer medição, registrar modelo exato, RAM, armazenamento, versão do
Android, API, patch de segurança e build/fingerprint exibido pelo aparelho. O
build não é inventado neste documento e não pode ser substituído por “mais
recente”. Se o Pixel 9a não estiver disponível, a troca de aparelho altera a
baseline e exige rationale e nova revisão desta subetapa.

## Dataset de referência

Usar dados sintéticos, sem informação pessoal real:

- fixture principal: `fixtures/standard-medium.project.json`;
- fixture leve: `fixtures/light-simple.project.json`;
- fixture de maior profundidade: `fixtures/deep.project.json`;
- corpus de desempenho futuro: 100 requisitos e 200 itens de roadmap, conforme
  limite canônico do primeiro piloto;
- pacote de exportação futuro: até 5 MB.

Cada execução registra fixture/hash, revisão do app, dispositivo, build do
sistema, estado da rede e tipo de build. Tempos de IA ficam fora das metas locais
e exigem provider/modelo/rede/tokens identificados quando F04 autorizar.

## Matriz mínima futura

| Ambiente | Checks obrigatórios quando houver APK | Momento autorizado |
|---|---|---|
| JVM host | Domínio, validações e adapters falsos determinísticos. | A partir de F02. |
| AVD API 26 | Instalar, abrir, fluxo principal, salvar/retomar, offline, exportar e testes de UI aplicáveis. | A partir de F02/F03, conforme capacidade implementada. |
| Pixel 9a API 36 | Jornada crítica, fonte 200%, TalkBack, modo avião, morte de processo e metas de desempenho aplicáveis. | Conforme F02–F10 entreguem cada capacidade. |
| Plataforma estável mais nova | Compatibilidade de comportamento sem alterar automaticamente `targetSdk`. | Antes do piloto/release, se diferente da baseline. |
| Preview API 37 | Exploração isolada; não bloqueia baseline nem publicação. | Opcional, após existir build. |

O guia oficial recomenda emulador para variar APIs/telas e aparelho real antes de
release. Esta matriz não promete cobertura universal de dispositivos.

## Comandos de verificação previstos

Quando F02 autorizar a criação do projeto, o checkout limpo deverá documentar e
executar, no mínimo:

```text
./gradlew --version
./gradlew clean assembleDebug
./gradlew test
./gradlew lint
./gradlew connectedDebugAndroidTest
```

No Windows, usar os equivalentes via `gradlew.bat`. O output deve registrar as
versões efetivamente resolvidas. Esta lista é critério reproduzível, não evidência
de execução atual: F01.05 não autoriza Gradle nem implementação Android.

## Fontes oficiais consultadas

- Android Studio estável e compatibilidade com AGP:
  <https://developer.android.com/studio/releases>
- Compatibilidade AGP 9.4.0, Gradle, Build Tools e JDK:
  <https://developer.android.com/build/releases/agp-9-4-0-release-notes>
- Compatibilidade entre Kotlin e AGP:
  <https://developer.android.com/build/kotlin-support>
- Releases do Kotlin:
  <https://kotlinlang.org/docs/releases.html>
- Compose BOM estável:
  <https://developer.android.com/develop/ui/compose/bom>
- Configuração do SDK Android 16:
  <https://developer.android.com/about/versions/16/setup-sdk>
- Relação entre API e `minSdk`:
  <https://developer.android.com/guide/topics/manifest/uses-sdk-element>
- Criação e gestão de AVDs:
  <https://developer.android.com/studio/run/managing-avds>
- Teste em aparelho físico:
  <https://developer.android.com/studio/run/device>
- Estratégia de testes Android:
  <https://developer.android.com/training/testing/fundamentals/strategies>
- Especificações oficiais do Pixel 9a:
  <https://support.google.com/pixelphone/answer/7158570>
- Releases do Android Emulator:
  <https://developer.android.com/studio/releases/emulator>

## Verificação de F01.05

- Aparelho físico intermediário e AVD da API mínima estão definidos.
- `minSdk`, `compileSdk`, `targetSdk` e toolchain possuem valores exatos e fontes
  oficiais vigentes.
- Dataset, metadados de execução e matriz de checks estão definidos.
- Nenhum build foi alegado: sua execução pertence às fases que autorizam Android.
- F01.06, Android, provider e Idea Factory não foram iniciados.
