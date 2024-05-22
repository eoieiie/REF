// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    id("com.google.devtools.ksp") version "1.9.24-1.0.20" apply false
}

buildscript {
    // ext.kotlin_version = "1.9.24" // (id("kotlin-android-extensions"))
    extra.apply {
        set("kotlin_version", "1.9.24")
    }
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.ksp.gradle.plugin) // classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.9.24-1.0.20")
        classpath(libs.gradle) // classpath "com.android.tools.build:gradle:8.3.2"

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}