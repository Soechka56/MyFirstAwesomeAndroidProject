plugins {
    alias(libs.plugins.app.android.library)
}

android {
    namespace = "com.example.di"
}

dependencies {
    implementation(libs.androidx.room.runtime)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(project(path = ":core:build-config:api"))
    implementation(project(path = ":core:build-config:impl"))
    implementation(project(path = ":core:data"))
    implementation(project(path = ":core:domain"))
    implementation(project(path = ":core:network"))
}
