# F02 — comandos reproduzíveis

Na raiz do repositório, com `ANDROID_HOME` apontando para o SDK:

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat test
.\gradlew.bat lint
```

O APK debug é gerado em `app/build/outputs/apk/debug/app-debug.apk`.
Nenhum comando depende de GitHub Actions.
