plugins {
    alias(libs.plugins.app.android.library)
}

android {
    namespace = "com.example.domain"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
