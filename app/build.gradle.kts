plugins {
    alias(libs.plugins.app.android.application)
    alias(libs.plugins.app.compose.screen)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.app.koin)
    alias(libs.plugins.gms.google.services)
    alias(libs.plugins.firebase.crashlytics.plugin)
}


android {
    namespace = "com.soechka1.myfirstawesomeandroidproject"
    defaultConfig {
        applicationId = "com.soechka1.myfirstawesomeandroidproject"
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.x.lifecycle.runtime.ktx)
    implementation(libs.x.activity.compose)
    implementation(libs.x.lifecycle.viewmodel.compose)
    implementation(libs.androidx.room.runtime)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.kotlinx.serialization.core)

    implementation(project(path = ":core:build-config:api"))
    implementation(project(path = ":core:build-config:impl"))
    implementation(project(path = ":core:di"))
    implementation(project(path = ":core:navigation"))
    implementation(project(path = ":core:analytics:api"))
    implementation(project(path = ":core:analytics:impl"))

    implementation(project(path=":core:domain"))
    implementation(project(path=":core:data"))
    implementation(project(path=":core:network"))
    implementation(project(path=":core:designsystem"))
    implementation(project(path=":core:ui"))
    implementation(project(path=":feature:getbattlelog:api"))
    implementation(project(path=":feature:getbattlelog:impl"))
    implementation(project(path=":feature:search:api"))
    implementation(project(path=":feature:search:impl"))

    // Firebase (BOM manages all versions automatically)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.messaging)
}
