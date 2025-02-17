plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "ru.binnyatoff.WeatherApp"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_KEY", "\"a1b1d9d873a369c867978a5b0d661a5d\"")
        }
        release {
            isMinifyEnabled = false
            buildConfigField("String", "API_KEY", "\"a1b1d9d873a369c867978a5b0d661a5d\"")
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
    }
    namespace = "ru.binnyatoff.weatherapp"
}

dependencies {
    //Fragment
    implementation("androidx.fragment:fragment-ktx:1.4.1")
    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")
    //SwipeToRefresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //HttpLoggingInterceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    //Dagger
    implementation("com.google.dagger:dagger:2.41")
    implementation("com.google.android.gms:play-services-location:19.0.1")
    kapt("com.google.dagger:dagger-compiler:2.41")
    //ViewBinding
    implementation("com.github.kirich1409:viewbindingpropertydelegate:1.5.6")
    //Glide
    implementation("com.github.bumptech.glide:glide:4.13.0")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}