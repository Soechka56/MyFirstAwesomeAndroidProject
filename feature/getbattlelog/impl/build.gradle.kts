plugins {
    alias(libs.plugins.app.android.library)
    alias(libs.plugins.app.compose.screen)
    alias(libs.plugins.app.koin)
}

android {
    namespace = "com.soechka1.myfirstawesomeandroidproject.feature.getbattlelog.impl"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.x.lifecycle.runtime.ktx)
    implementation(libs.x.activity.compose)
    implementation(libs.x.lifecycle.viewmodel.compose)
    implementation(libs.x.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation3.runtime)

    implementation(project(path = ":core:domain"))
    implementation(project(path = ":core:designsystem"))
    implementation(project(path = ":core:navigation"))
    implementation(project(path = ":core:ui"))
    implementation(project(path = ":feature:getbattlelog:api"))

    implementation(project(path = ":core:build-config:api"))
}
