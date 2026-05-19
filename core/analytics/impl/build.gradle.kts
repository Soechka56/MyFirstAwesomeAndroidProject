plugins {
    alias(libs.plugins.app.android.library)
    alias(libs.plugins.app.koin)
}

android {
    namespace = "com.example.analytics.impl"
}

dependencies {
    implementation(project(path = ":core:analytics:api"))
    implementation(project(path = ":core:build-config:api"))

    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
}
