import io.matthewnelson.kotlin.components.dependencies.deps
import io.matthewnelson.kotlin.components.dependencies.depsKapt
import io.matthewnelson.kotlin.components.dependencies.versions
import io.matthewnelson.kotlin.components.kmp.KmpTarget
import io.matthewnelson.kotlin.components.kmp.kapt

plugins {
    id("kmp-configuration")
    id("dependencies")
}

kmpConfiguration {
    setupMultiplatform(
        setOf(
            KmpTarget.Jvm.Android(
                buildTools = versions.buildTools,
                compileSdk = versions.sdkCompile,
                minSdk = versions.sdkMin16,
                pluginIds = setOf("kotlin-kapt", "dagger.hilt.android.plugin"),
                androidConfig = {
                    buildFeatures.viewBinding = true
                },
                mainSourceSet = {
                    dependencies {
                        api(deps.androidx.navigation.fragment)
                        implementation(deps.viewBindingDelegateNoReflect)
                        implementation(deps.androidx.constraintLayout)
                        implementation(deps.google.hilt)
                        kapt(project, depsKapt.google.hilt)
                    }
                }
            ),
        ),

        commonMainSourceSet = {
            dependencies {
                api(project(":extensions:navigation"))
            }
        },
        commonTestSourceSet = null
    )
}
