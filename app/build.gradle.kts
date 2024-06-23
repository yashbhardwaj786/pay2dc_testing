import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.parcelize")
    id("kotlin-android")
//    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    val localProperties = Properties()
    localProperties.load(project.rootProject.file("local.properties").inputStream())
    compileSdk = 34

    defaultConfig {
        namespace = "com.arpit.pay2dc"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    flavorDimensions.add("dim")
    productFlavors {
        create("prod") {
            dimension = "dim"
            buildConfigField(
                "String",
                "API_KEY",
                localProperties.getValue("API_KEY") as String
            )
        }
        create("dev") {
            dimension = "dim"
            buildConfigField(
                "String",
                "API_KEY",
                localProperties.getValue("API_KEY") as String
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

kotlin {
    jvmToolchain(8)
}

dependencies {
    implementation(Dependencies.kotlin)
    implementation(Dependencies.core_ktx)
    implementation(Dependencies.constraintlayout)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.material_design)

    implementation(Dependencies.viewModel)
    implementation(Dependencies.fragment_ktx)
    kapt(Dependencies.coroutine)
    kapt (Dependencies.Hilt.hilt_compiler)
    implementation (Dependencies.Hilt.hilt_android)
    implementation (Dependencies.Retrofit.retrofit)
    implementation (Dependencies.Retrofit.logging_interceptor)

    implementation (Dependencies.Retrofit.main)
    implementation (Dependencies.Moshi.moshi)
    implementation (Dependencies.Moshi.moshi_kotlin)
    implementation (Dependencies.Compose.compose_ui)
    implementation (Dependencies.Compose.compose_material)
    implementation (Dependencies.Compose.tooling_ui)
    implementation (Dependencies.Compose.compose_activity)
    implementation (Dependencies.Compose.compose_viewmodel)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.espresso_core)
    testImplementation("org.mockito:mockito-inline:4.8.0")
    testImplementation("com.nhaarman:mockito-kotlin:1.6.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    testImplementation("org.json:json:20220924")
//    testImplementation 'com.squareup.assertj:assertj-android:1.2.0'
}

kapt {
    correctErrorTypes = true
}