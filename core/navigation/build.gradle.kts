plugins {
    alias(libs.plugins.app.android.library)
}

android {
    namespace = "com.soechka1.myfirstawesomeandroidproject.core.navigation"
}

dependencies {
    implementation(libs.androidx.navigation3.runtime)
    implementation(project(path = ":core:build-config:api"))
    implementation(project(path = ":core:domain"))
}
