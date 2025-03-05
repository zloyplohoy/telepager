import ag.sokolov.telepager.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinConventionPlugin : Plugin<Project>  {
    override fun apply(target: Project) {
        with(target) {
            // TODO: Review plugin application rules

            pluginManager.apply("com.google.devtools.ksp")

            dependencies {
                val bom = libs.findLibrary("koin.bom").get()
                add("implementation", platform(bom))
                add("implementation", libs.findLibrary("koin.core").get())
            }

            pluginManager.withPlugin("com.android.application") {
                dependencies {
                    add("implementation", libs.findLibrary("koin.android").get())
                }
            }

            pluginManager.withPlugin("com.android.library") {
                dependencies {
                    add("implementation", libs.findLibrary("koin.android").get())
                }
            }

            pluginManager.withPlugin("org.jetbrains.kotlin.plugin.compose") {
                dependencies {
                    add("implementation", libs.findLibrary("koin.compose.viewmodel.navigation").get())
                }
            }
        }
    }
}
