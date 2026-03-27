pluginManagement {
    includeBuild("convention-plugins")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyFirstAwesomeAndroidProject"
include(":app")
include(":core:network")
include(":core:build-config")
include(":core:build-config:api")
include(":core:build-config:impl")
include(":core:domain")
include(":core:navigation")
include(":feature")
include(":feature:getbattlelog:api")
include(":feature:getbattlelog:impl")
include(":feature:search:api")
include(":feature:search:impl")
include(":core:data")
include(":core:designsystem")
include(":core:ui")
include(":core:di")
