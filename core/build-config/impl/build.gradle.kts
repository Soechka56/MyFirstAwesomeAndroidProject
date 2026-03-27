plugins {
    alias(libs.plugins.app.android.library)
    alias(libs.plugins.gradle.secrets)
}

var keyValue = ""
val propsFile = File("properties")
if (propsFile.exists()) {
    keyValue = propsFile.readText()
}

android {
    namespace = "com.example.impl"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "BRAWL_STARS_BASE_URL", "\"https://api.brawlstars.com/v1/\"")
        // https://www.youtube.com/watch?v=GBIIQ0kP15E
        buildConfigField("String", "BRAWLER_CDN_BASE_URL", "\"https://cdn.brawlify.com/\"")
        buildConfigField("String", "BRAWL_STARS_API_KEY", "\"$keyValue\"")
    }
}

dependencies {
    implementation(project(path = ":core:build-config:api"))
}
