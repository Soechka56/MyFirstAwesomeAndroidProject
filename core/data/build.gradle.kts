plugins {
    alias(libs.plugins.app.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(project(path=":core:domain"))
    implementation(project(path=":core:network"))
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}
