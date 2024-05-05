plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")

    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.whatmovie.app.compose.data"

    compileSdk = Versions.ANDROID_COMPILE_SDK_VERSION
    defaultConfig {
        minSdk = Versions.ANDROID_MIN_SDK_VERSION
        consumerProguardFiles("consumer-rules.pro")
        // Room schema export to allow db auto migrations between versions
        ksp {
            arg("room.schemaLocation", "$projectDir/database/roomSchemas")
        }
    }

    packaging {
        resources.excludes.add("META-INF/LICENSE.md")
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
            )
        }

        getByName(BuildType.DEBUG) {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(Module.DOMAIN))

    api("androidx.room:room-runtime:${Versions.ROOM_DB_VERSION}")
    api("androidx.room:room-ktx:${Versions.ROOM_DB_VERSION}")
    ksp("androidx.room:room-compiler:${Versions.ROOM_DB_VERSION}")

    api("com.jakewharton.timber:timber:${Versions.TIMBER_LOG_VERSION}")

    implementation("androidx.core:core-ktx:${Versions.ANDROIDX_CORE_KTX_VERSION}")
    implementation("androidx.datastore:datastore-preferences:${Versions.ANDROIDX_DATASTORE_PREFERENCES_VERSION}")
    implementation("androidx.security:security-crypto:${Versions.ANDROID_CRYPTO_VERSION}")
    implementation("com.google.dagger:hilt-android:${Versions.HILT_VERSION}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}")
    implementation("javax.inject:javax.inject:${Versions.JAVAX_INJECT_VERSION}")


    api("com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT_VERSION}")
    api("com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}")

    api("com.squareup.moshi:moshi-adapters:${Versions.MOSHI_VERSION}")
    api("com.squareup.moshi:moshi-kotlin:${Versions.MOSHI_VERSION}")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI_VERSION}")

    api("com.squareup.okhttp3:okhttp:${Versions.OKHTTP_VERSION}")
    api("com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP_VERSION}")

    // Testing
    testImplementation("junit:junit:${Versions.TEST_JUNIT_VERSION}")
    testImplementation("io.mockk:mockk:${Versions.TEST_MOCKK_VERSION}")
    testImplementation("io.kotest:kotest-assertions-core:${Versions.TEST_KOTEST_VERSION}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KOTLINX_COROUTINES_VERSION}")
//    testImplementation("androidx.test:core:${Versions.TEST_ANDROIDX_CORE_VERSION}")
    testImplementation("androidx.test.ext:junit:${Versions.TEST_EXT_JUNIT}")
    testImplementation("org.robolectric:robolectric:${Versions.TEST_ROBOLECTRIC_VERSION}")
//    testImplementation("app.cash.turbine:turbine:${Versions.TEST_TURBINE_VERSION}")
}
