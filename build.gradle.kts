plugins {
    id("com.android.application") version "${Versions.BUILD_GRADLE_VERSION}" apply false
    id("com.android.library") version "${Versions.BUILD_GRADLE_VERSION}" apply false
    id("org.jetbrains.kotlin.android") version "1.8.20" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.BUILD_GRADLE_VERSION}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT_VERSION}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_VERSION}")
        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:${Versions.KOTLIN_VERSION}-1.0.11")

    }
}