import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.itis.android.core.plugin.ext.libs

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        dependencies {
            "implementation"(libs.findLibrary("koin.android").get())
            "implementation"(libs.findLibrary("koin.compose.viewmodel").get())
            "implementation"(libs.findLibrary("koin.navigation3").get())
        }
    }
}
