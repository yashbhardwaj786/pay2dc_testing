
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.build_gradle)
        classpath(Dependencies.build_gradle_plugin)
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51")
//        classpath("com.google.devtools.ksp:symbol-processing-gradle-plugin:1.8.10-1.0.9")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("type", Delete::class) {
    delete(rootProject.buildDir)
}