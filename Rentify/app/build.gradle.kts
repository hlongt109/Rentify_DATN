plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.rentify.user.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rentify.user.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        compose = true
    }
}

dependencies {
    // _vanphuc :
    implementation ("io.ktor:ktor-client-core:2.2.3")
    implementation ("io.ktor:ktor-client-cio:2.2.3")
    implementation ("io.ktor:ktor-client-serialization:2.2.3")
    // thư viện tải ảnh _vanphuc :
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")
    implementation ("androidx.media3:media3-exoplayer:1.0.0")
    implementation ("androidx.media3:media3-ui:1.0.0")

    implementation ("io.coil-kt:coil-compose:2.4.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    //implementation(libs.androidx.media3.exoplayer)
  // implementation(libs.androidx.media3.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    val nav_version = "2.8.0"
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.2")

    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.0")
    implementation ("androidx.compose.material:material-icons-extended-android:1.7.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")
    implementation ("androidx.activity:activity-compose:1.9.2")

    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("androidx.compose.material:material:1.7.0")
    implementation ("com.google.android.material:material:1.12.0")
    // Hild
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    // Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    // Asynchronous
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.3.0"))
    implementation("com.google.firebase:firebase-analytics")

    debugImplementation("androidx.compose.ui:ui-tooling:1.7.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.0")

    // SplashScreen
    implementation("androidx.core:core-splashscreen:1.0.0")
    // Bottom Sheet Dialog
    implementation("androidx.compose.material:material:1.7.5")
    //
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation ("com.google.accompanist:accompanist-flowlayout:0.36.0")


    //wipe to delete
    implementation ("androidx.compose.foundation:foundation:1.7.5")
    implementation ("androidx.compose.material:material-icons-extended:1.7.5")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation ("io.ktor:ktor-client-android:2.3.3")
    implementation ("io.ktor:ktor-client-json:2.3.3")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation ("io.ktor:ktor-client-core:2.2.3")
    implementation ("io.ktor:ktor-client-cio:2.2.3")
    implementation ("io.ktor:ktor-client-serialization:2.2.3")
    // thư viện tải ảnh _vanphuc :
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("com.google.android.exoplayer:exoplayer:2.18.1")

        implementation ("com.google.accompanist:accompanist-pager:0.28.0") // Thêm dòng này
        implementation ("com.google.accompanist:accompanist-pager-indicators:0.28.0") // Thêm dòng này nếu cần indicators (dấu chấm chỉ số trang)


}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}

