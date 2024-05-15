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
    }
    buildFeatures {
        viewBinding = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // RoomDB
    implementation(libs.room.runtime) // implementation("androidx.room:room-runtime:2.6.1")
    implementation(libs.room.ktx) // implementation("androidx.room:room-ktx:2.6.1")
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