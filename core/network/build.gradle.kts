plugins {
    alias(libs.plugins.app.android.library)
    alias(libs.plugins.app.koin)
}

android {
    namespace = "com.example.network"

    buildTypes {
        debug{}
        release{}
    }

    buildFeatures {
        buildConfig = true
    }

}

dependencies {
    implementation(project(path = ":core:build-config:api"))
    implementation(project(path = ":core:build-config:impl"))
    implementation(libs.retrofit.converter.gson)
}
