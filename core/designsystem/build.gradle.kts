plugins {
    alias(libs.plugins.app.android.library)
    alias(libs.plugins.app.compose.base)
}

android {
    namespace = "com.example.designsystem"

}

dependencies {
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(project(path = ":core:domain"))
    implementation(libs.androidx.activity)
}
