import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.itis.android.core.plugin.ext.libs

class ComposeScreenConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.findPlugin("app-compose-base").get().get().pluginId)

        dependencies {
            "implementation"(libs.findLibrary("x.lifecycle.viewmodel.compose").get())
            "implementation"(libs.findLibrary("x.lifecycle.runtime.compose").get())
        }
    }
}