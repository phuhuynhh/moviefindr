plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

val keystoreProperties = rootDir.loadGradleProperties("signing.properties")
val BASE_API_URL = properties.getValue("BASE_API_URL") as String ?: null
val API_TOKEN = properties.getValue("BASE_API_TOKEN") as String ?: null


android {
    namespace = "com.whatmovie.app.compose"
    compileSdk = Versions.ANDROID_COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "com.whatmovie.app.compose"
        minSdk = Versions.ANDROID_MIN_SDK_VERSION
        targetSdk = Versions.ANDROID_TARGET_SDK_VERSION
        versionCode = Versions.ANDROID_VERSION_CODE
        versionName = Versions.ANDROID_VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packagingOptions { resources.excludes.add("META-INF/*") }


    signingConfigs {
        create(BuildType.RELEASE) {
            // Remember to edit signing.properties to have the correct info for release build.
            storeFile = file("../config/release.keystore")
            storePassword = keystoreProperties.getProperty("KEYSTORE_PASSWORD") as String
            keyPassword = keystoreProperties.getProperty("KEY_PASSWORD") as String
            keyAlias = keystoreProperties.getProperty("KEY_ALIAS") as String
        }
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")

            signingConfig = signingConfigs[BuildType.RELEASE]
            BASE_API_URL?.let {
                buildConfigField("String", "BASE_API_URL", "${BASE_API_URL}")
            }
            API_TOKEN?.let {
                buildConfigField("String", "API_TOKEN", "${API_TOKEN}")
            }
        }

        getByName(BuildType.DEBUG) {
            // For quickly testing build with proguard, enable this
            isMinifyEnabled = false

            signingConfig = signingConfigs[BuildType.DEBUG]
            BASE_API_URL?.let {
                buildConfigField("String", "BASE_API_URL", "${BASE_API_URL}")
            }
            API_TOKEN?.let {
                buildConfigField("String", "API_TOKEN", "${API_TOKEN}")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER_VERSION
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
        packagingOptions { jniLibs { useLegacyPackaging = true } }
    }


}

dependencies {
    implementation(project(Module.DATA))
    implementation(project(Module.DOMAIN))


    implementation("androidx.core:core-ktx:${Versions.ANDROIDX_CORE_KTX_VERSION}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE_VERSION}")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:${Versions.ANDROIDX_LIFECYCLE_VERSION}")

    implementation("androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}")
    implementation("androidx.core:core-splashscreen:${Versions.ANDROIDX_CORE_SPLASH_SCREEN_VERSION}")
    implementation(platform("androidx.compose:compose-bom:${Versions.COMPOSE_BOM_VERSION}"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.foundation:foundation-layout")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")


    implementation("io.coil-kt:coil-compose:${Versions.COIL_COMPOSE_VERSION}")

    implementation("androidx.navigation:navigation-compose:${Versions.COMPOSE_NAVIGATION_VERSION}")
    implementation("com.google.accompanist:accompanist-permissions:${Versions.ACCOMPANIST_PERMISSIONS_VERSION}")

    implementation("androidx.datastore:datastore-preferences:${Versions.ANDROIDX_DATASTORE_PREFERENCES_VERSION}")

    implementation("com.google.dagger:hilt-android:${Versions.HILT_VERSION}")
    implementation("androidx.hilt:hilt-navigation-compose:${Versions.HILT_NAVIGATION_COMPOSE_VERSION}")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}")

    kapt("com.google.dagger:hilt-compiler:${Versions.HILT_VERSION}")

    debugImplementation("com.github.chuckerteam.chucker:library:${Versions.CHUCKER_VERSION}")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:${Versions.CHUCKER_VERSION}")

    // Unit test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KOTLINX_COROUTINES_VERSION}")
    testImplementation("io.kotest:kotest-assertions-core:${Versions.TEST_KOTEST_VERSION}")
    testImplementation("junit:junit:${Versions.TEST_JUNIT_VERSION}")
    testImplementation("io.mockk:mockk:${Versions.TEST_MOCKK_VERSION}")
    testImplementation("app.cash.turbine:turbine:${Versions.TEST_TURBINE_VERSION}")

    // UI test with Robolectric
    testImplementation(platform("androidx.compose:compose-bom:${Versions.COMPOSE_BOM_VERSION}"))
    testImplementation("androidx.compose.ui:ui-test-junit4")
    testImplementation("androidx.test:rules:${Versions.TEST_RULES_VERSION}")
    testImplementation("org.robolectric:robolectric:${Versions.TEST_ROBOLECTRIC_VERSION}")

    // UI test
    androidTestImplementation(platform("androidx.compose:compose-bom:${Versions.COMPOSE_BOM_VERSION}"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.test:rules:${Versions.TEST_RULES_VERSION}")
    androidTestImplementation("io.mockk:mockk-android:${Versions.TEST_MOCKK_VERSION}")
}
