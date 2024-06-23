object Versions {
    const val build_gradle = "7.2.2"
    const val kotlin_version = "1.9.10"
    const val core_ktx = "1.10.0"
    const val appcompat = "1.6.1"
    const val material_design = "1.8.0"
    const val constraintlayout = "2.1.4"
    const val espresso_core = "3.4.0"
    const val junit = "1.1.3"

    const val coroutine = "1.3.9"
    const val viewModel = "2.5.1"
    const val fragment_ktx = "1.5.5"
    const val hilt = "2.51"
    const val retrofit = "2.9.0"
    const val moshi_kotlin = "1.14.0"
    const val okhttp = "4.2.0"
    const val compose_version = "1.5.4"
    const val activity_compose_version = "1.8.2"
    const val compose_version_vm = "2.6.2"
}

object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
    const val build_gradle = "com.android.tools.build:gradle:${Versions.build_gradle}"
    const val build_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material_design = "com.google.android.material:material:${Versions.material_design}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
    const val junit = "androidx.test.ext:junit:${Versions.junit}"

    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModel}"
    const val viewModel_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.viewModel}"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val main = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val logging_interceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    }

    object Moshi {
        const val moshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        const val moshi_kotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi_kotlin}"
    }

    object Hilt {
        const val hilt_android = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    }

    object Compose {
        const val compose_ui = "androidx.compose.ui:ui-tooling:${Versions.compose_version}"
        const val compose_material = "androidx.compose.material:material:${Versions.compose_version}"
        const val tooling_ui = "androidx.compose.ui:ui-tooling:${Versions.compose_version}"
        const val compose_activity = "androidx.activity:activity-compose:${Versions.activity_compose_version}"
        const val compose_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.compose_version_vm}"
    }
}