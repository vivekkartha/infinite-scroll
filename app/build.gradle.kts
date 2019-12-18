plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
}


android {
    compileSdkVersion(Config.Android.compileSdk)

    defaultConfig {
        applicationId = "com.glovoapp.feed"
        minSdkVersion(Config.Android.minSdk)
        targetSdkVersion(Config.Android.targetSdk)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFile("proguard-rules.pro")
        }
    }

}


dependencies {
    // Kotlin
    implementation(kotlinStdlib)

    // Android Support
    implementation(Config.Libraries.appcompatV7)
    implementation(Config.Libraries.recyclerView)
    implementation(Config.Libraries.cardView)
    implementation(Config.Libraries.constraintLayout)
    implementation(Config.Libraries.constraintLayoutSolver)

    implementation(Config.Libraries.coroutinesCore)
    implementation(Config.Libraries.coroutinesAndroid)
    implementation(Config.Libraries.viewModels)
    implementation(Config.Libraries.lifeCycleExt)
    implementation(Config.Libraries.pagingLib)

    implementation(Config.Libraries.rxJava)
    implementation(Config.Libraries.rxAndroid)
    implementation(Config.Libraries.rxKotlin)

    // Test
    testImplementation(Config.Testing.junit)

    testImplementation(Config.Testing.mockitoCore)
    androidTestImplementation(Config.Testing.mockitoCore)

    androidTestImplementation(Config.Testing.testRunner)

    androidTestImplementation(Config.Testing.espressoCore)

    testImplementation(Config.Testing.powerMockCore)
    testImplementation(Config.Testing.powerMockApi)
    testImplementation(Config.Testing.powerMockJunit)
    testImplementation(Config.Testing.powerMockJunitRule)

}

