plugins {
    alias(libs.plugins.androidApplication) // id("com.android.application")
    alias(libs.plugins.jetbrainsKotlinAndroid) // id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") // id ("kotlin-kapt")
}

android {
    namespace = "com.fsof.project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fsof.project"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        viewBinding = true
//        compose = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.14"
//    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
}

dependencies {

//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // TFLite
    implementation(libs.tflite)
    implementation(libs.tflite.support)

    // RoomDB
    implementation(libs.room.runtime) // implementation("androidx.room:room-runtime:2.6.1")
    implementation(libs.room.ktx)
    // To use Kotlin annotation processing tool (KSP) // (kapt)
    ksp(libs.room.compiler) // ksp("androidx.room:room-compiler:2.6.1") // kapt("androidx.room:room-compiler:2.6.1") // annotationProcessor("androidx.room:room-compiler:2.6.1")

    // Retrofit
    implementation(libs.retrofit) // implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation(libs.gson) // implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Okhttp
    // define a BOM and its version
    // implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))

    // define any required OkHttp artifacts without version
    implementation(libs.okhttp) // implementation("com.squareup.okhttp3:okhttp") // implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation(libs.loging.interceptor) // implementation("com.squareup.okhttp3:logging-interceptor") // implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}