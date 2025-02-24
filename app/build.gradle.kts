plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)

}

android {
    namespace = "com.example.shoesecommerce"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.shoesecommerce"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures{
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "35.0.0"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.database)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.1.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")
    implementation("com.google.accompanist:accompanist-pager:0.28.0")

    implementation(libs.androidx.ui.test.android)
    implementation(libs.material)
    implementation(libs.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)



        // Import the BoM for the Firebase platform
    implementation(libs.firebase.bom)
        // Add the dependency for the Realtime Database library
        // When using the BoM, you don't specify versions in Firebase library dependencies
        implementation(libs.google.firebase.database)
    implementation (libs.kotlin.script.runtime)
    implementation("com.google.firebase:firebase-analytics-ktx:21.5.0")


}