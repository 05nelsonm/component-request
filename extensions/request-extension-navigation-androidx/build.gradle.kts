import io.matthewnelson.kotlin.components.dependencies.deps
import io.matthewnelson.kotlin.components.dependencies.versions
import io.matthewnelson.kotlin.components.kmp.KmpTarget

plugins {
    id("kmp-configuration")
    id("kmp-publish")
}

kmpConfiguration {
    setupMultiplatform(
        setOf(
            KmpTarget.Jvm.Android(
                buildTools = versions.android.buildTools,
                compileSdk = versions.android.sdkCompile,
                minSdk = versions.android.sdkMin16,
                target = {
                    publishLibraryVariants("release")
                },
                mainSourceSet = {
                    dependencies {
                        implementation(deps.androidx.navigation.fragment)
                    }
                }
            ),
        ),
        commonMainSourceSet = {
            dependencies {
                api(project(":extensions:request-extension-navigation"))
            }
        }
    )
}

kmpPublish {
    setupModule(
        pomDescription = "Kotlin Components' Request Concept extension for androidx navigation",
        holdPublication = false,
    )
}
