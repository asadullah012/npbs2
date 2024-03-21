plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.galib.natorepbs2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.galib.natorepbs2"
        minSdk = 21
        targetSdk = 34
        versionCode = 7
        versionName = "2.0.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
        }
    }
    dataBinding {
        enable = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    packaging {
        resources {
            excludes += "/META-INF/atomicfu.kotlin_module"
        }
    }
//    testOptions {
//        unitTests.returnDefaultValues = true
//    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
}

configurations {
    implementation {
        exclude(module = "guava-jdk5")
        exclude(group = "com.google.guava", module = "listenablefuture")
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
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
    annotationProcessor(libs.androidx.room.room.compiler)
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
    kapt (libs.compiler)
    implementation(libs.androidx.navigation.fragment.ktx)

    //Compose
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-window-size-class")
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
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1") {
        exclude(group = "com.android.support", module = "support-annotations")
    }
    androidTestImplementation (libs.androidx.junit)
}

kapt {
    generateStubs = true
}