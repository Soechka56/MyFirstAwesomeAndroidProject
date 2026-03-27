plugins {
    alias(libs.plugins.app.android.library)
    alias(libs.plugins.app.compose.screen)
}

android {
    namespace = "com.soechka1.myfirstawesomeandroidproject.feature.search.impl"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.x.activity.compose)
    implementation(libs.x.lifecycle.runtime.ktx)
    implementation(libs.x.lifecycle.viewmodel.compose)
    implementation(libs.x.lifecycle.viewmodel.ktx)
    implementation(project(path = ":core:di"))
    implementation(project(path = ":core:domain"))
    implementation(project(path = ":core:designsystem"))
    implementation(project(path = ":core:ui"))
    implementation(project(path = ":feature:getbattlelog:impl"))
}
