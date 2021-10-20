import io.matthewnelson.kotlin.components.dependencies.deps
import io.matthewnelson.kotlin.components.dependencies.versions
import io.matthewnelson.kotlin.components.kmp.KmpTarget

plugins {
    id("dependencies")
    id("kmp-configuration")
    id("kmp-publish")
}

kmpConfiguration {
    setupMultiplatform(
        setOf(
            KmpTarget.Jvm.Android(
                buildTools = versions.buildTools,
                compileSdk = versions.sdkCompile,
                minSdk = versions.sdkMin16,
                target = {
                    publishLibraryVariants("release")
                },
                mainSourceSet = {
                    dependencies {
                        api(project(":request-concept"))
                        implementation(deps.androidx.navigation.fragment)
                    }
                }
            ),
        )
    )
}

kmpPublish {
    setupModule(
        pomArtifactId = "request-extension-navigation-androidx",
        pomDescription = "Kotlin Components' Request Concept extension for androidx navigation"
    )
}
