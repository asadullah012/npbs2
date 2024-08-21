import org.jetbrains.kotlin.gradle.dsl.JvmTarget

class AppConfig {
    val id = "com.galib.natorepbs2"
    val versionCode = 9
    val versionName = "2.0.1"

    val compileSdk = libs.versions.compileSdk.get().toInt()
    val minSdk = libs.versions.minSdk.get().toInt()
    val targetSdk = libs.versions.targetSdk.get().toInt()

    val jvmTarget = JvmTarget.JVM_17
    val javaVersion = JavaVersion.VERSION_17
    val multiDexEnabled = true
    val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}


plugins {
    alias(libs.plugins.android.application)
    id("androidx.navigation.safeargs")
    id("kotlin-android")
    id("kotlin-kapt")
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    alias(libs.plugins.compose.compiler)
}

val appConfig = AppConfig()

android {
    namespace = appConfig.id
    compileSdk = appConfig.compileSdk

    defaultConfig {
        applicationId = appConfig.id
        minSdk = appConfig.minSdk
        targetSdk = appConfig.targetSdk
        versionCode = appConfig.versionCode
        versionName = appConfig.versionName
        multiDexEnabled = appConfig.multiDexEnabled
        testInstrumentationRunner = appConfig.testInstrumentationRunner
    }

    compileOptions {
        sourceCompatibility = appConfig.javaVersion
        targetCompatibility = appConfig.javaVersion
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = true
        }
    }
    dataBinding {
        enable = true
    }
    packaging {
        resources {
            excludes += "/META-INF/atomicfu.kotlin_module"
        }
    }
//    testOptions {
//        unitTests.returnDefaultValues = true
//    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(appConfig.jvmTarget)
    }
}

configurations {
    implementation {
        exclude(module = "guava-jdk5")
        exclude(group = "com.google.guava", module = "listenablefuture")
    }
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.activity.ktx)

    // Room components
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation( libs.flexbox)
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)
    androidTestImplementation (libs.androidx.room.testing)

    // Lifecycle components
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.common.java8)

    //Kotlin component
    implementation(libs.kotlinx.coroutines.android)
    implementation( libs.kotlin.stdlib.jdk8)
    api (libs.kotlinx.coroutines.core)
    api (libs.kotlinx.coroutines.android)

    // UI
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    ksp (libs.compiler)
    implementation(libs.androidx.navigation.fragment.ktx)

    //Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.window.sizeclass)
    implementation(libs.androidx.constraintlayout.compose)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.runtime)
    implementation(libs.androidx.runtime.livedata)

    //In-app Update
    implementation(libs.app.update)

    // Other
    implementation(libs.jsoup)
    implementation(libs.android.pdf.viewer)
    implementation(libs.picasso)
    implementation(libs.okhttp)

    // Testing
    testImplementation (libs.junit)
    // Optional -- Mockito framework
    testImplementation (libs.mockito.core)
    // Optional -- mockito-kotlin
    testImplementation(libs.mockito.kotlin)
    // Optional -- Mockk framework
    testImplementation (libs.mockk)
    androidTestImplementation (libs.androidx.core.testing)
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1") {
        exclude(group = "com.android.support", module = "support-annotations")
    }
    androidTestImplementation (libs.androidx.junit)
}

kapt {
    generateStubs = true
}