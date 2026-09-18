plugins { alias(libs.plugins.android.application) }

android {
    namespace = "com.playertwo.ideas"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.playertwo.ideas"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0"
    }
    buildTypes { release { isMinifyEnabled = false } }
    testOptions { unitTests.isIncludeAndroidResources = true }
}

dependencies {
    testImplementation(libs.junit)
}
