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

            KmpTarget.Jvm.Jvm(kotlinJvmTarget = JavaVersion.VERSION_1_8),

            KmpTarget.Jvm.Android(
                buildTools = versions.android.buildTools,
                compileSdk = versions.android.sdkCompile,
                minSdk = versions.android.sdkMin16,
                kotlinJvmTarget = JavaVersion.VERSION_1_8,
                compileSourceOption = JavaVersion.VERSION_1_8,
                compileTargetOption = JavaVersion.VERSION_1_8,
                target = {
                    publishLibraryVariants("release")
                },
                mainSourceSet = {
                    dependencies {
                        implementation(deps.androidx.navigation.fragment)
                    }
                }
            ),

            KmpTarget.NonJvm.JS(
                compilerType = org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType.BOTH,
                browser = KmpTarget.NonJvm.JS.Browser(
                    jsBrowserDsl = null
                ),
                node = KmpTarget.NonJvm.JS.Node(
                    jsNodeDsl = null
                ),
                mainSourceSet = null,
                testSourceSet = null,
            ),

            KmpTarget.NonJvm.Native.Unix.Darwin.Ios.All(enableSimulator = {}),
            KmpTarget.NonJvm.Native.Unix.Darwin.Macos.X64.DEFAULT,
            KmpTarget.NonJvm.Native.Unix.Darwin.Macos.Arm64.DEFAULT,
            KmpTarget.NonJvm.Native.Unix.Darwin.Tvos.All(enableSimulator = {}),
            KmpTarget.NonJvm.Native.Unix.Darwin.Watchos.All(enableSimulator = {}),

            KmpTarget.NonJvm.Native.Unix.Linux.X64.DEFAULT,

            KmpTarget.NonJvm.Native.Mingw.X64.DEFAULT,
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
    )
}
