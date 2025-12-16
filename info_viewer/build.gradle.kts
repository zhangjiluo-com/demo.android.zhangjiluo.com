plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.zhangjiluo.app.demo.info_viewer"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.zhangjiluo.app.demo.info_viewer"
        minSdk = 16
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
}
