plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.zhangjiluo.app.demo.tv_box"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.zhangjiluo.app.demo.tv_box"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "0.0.1"
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("androidx.constraintlayout:constraintlayout:2.2.1") // 约束布局
}
