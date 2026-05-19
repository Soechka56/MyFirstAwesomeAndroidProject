plugins {
    alias(libs.plugins.app.android.library)
}

android {
    namespace = "com.soechka1.myfirstawesomeandroidproject.core.navigation"
}

dependencies {
    implementation(libs.androidx.navigation3.runtime)
}
