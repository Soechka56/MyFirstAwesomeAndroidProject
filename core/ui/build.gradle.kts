plugins {
    alias(libs.plugins.app.android.library)
    alias(libs.plugins.app.compose.base)
}

android {
    namespace = "com.example.ui"
}

dependencies {
    implementation(project(path=":core:designsystem"))
    implementation(project(path=":core:domain"))
    implementation(libs.coil.compose)
}
