plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.artifact"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.artifact"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField ("String", "BASE_URL", "\"https://artifact-backend-1096927434244.asia-southeast2.run.app/api/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
        mlModelBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx.v1101)
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.androidx.constraintlayout.v214)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.room.common)
//    implementation(libs.androidx.animation.graphics.android)
    implementation(libs.androidx.core.animation)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.room.ktx)
    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.ui.desktop)
//    implementation(libs.androidx.ui.desktop)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.material)
    implementation(libs.logging.interceptor.v4110)
    implementation(libs.androidx.datastore.preferences)
    implementation (libs.glide)
    annotationProcessor (libs.compiler)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.tensorflow.lite.task.vision)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.lifecycle.runtime.ktx.v262)
}