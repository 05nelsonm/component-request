import io.matthewnelson.kotlin.components.dependencies.deps
import io.matthewnelson.kotlin.components.dependencies.depsTest
import io.matthewnelson.kotlin.components.dependencies.versions
import io.matthewnelson.kotlin.components.kmp.KmpTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType

plugins {
    id("dependencies")
    id("kmp-configuration")
    id("kmp-publish")
}

kmpConfiguration {
    setupMultiplatform(
        setOf(

            KmpTarget.Jvm.Jvm(
                testSourceSet = {
                    dependencies {
                        implementation(depsTest.kotlin.coroutines)
                        implementation(kotlin("test-junit"))
                    }
                }
            ),

            KmpTarget.Jvm.Android(
                buildTools = versions.buildTools,
                compileSdk = versions.sdkCompile,
                minSdk = versions.sdkMin16,
                target = {
                    publishLibraryVariants("release")
                },
                mainSourceSet = {
                    dependencies {
                        implementation(deps.androidx.lifecycle.commonJava8)
                        implementation(deps.androidx.lifecycle.runtime)
                    }
                }
            ),

            KmpTarget.NonJvm.JS(
                compilerType = KotlinJsCompilerType.BOTH,
                browser = KmpTarget.NonJvm.JS.Browser(
                    jsBrowserDsl = null
                ),
                node = KmpTarget.NonJvm.JS.Node(
                    jsNodeDsl = null
                ),
                mainSourceSet = null,
                testSourceSet = {
                    dependencies {
                        implementation(kotlin("test-js"))
                    }
                },
            ),

            KmpTarget.NonJvm.Native.Unix.Darwin.Ios.All.DEFAULT,
            KmpTarget.NonJvm.Native.Unix.Darwin.Macos.X64.DEFAULT,
            KmpTarget.NonJvm.Native.Unix.Darwin.Macos.Arm64.DEFAULT,
            KmpTarget.NonJvm.Native.Unix.Darwin.Tvos.All.DEFAULT,
            KmpTarget.NonJvm.Native.Unix.Darwin.Watchos.All.DEFAULT,
            KmpTarget.NonJvm.Native.Unix.Linux.X64.DEFAULT,
            KmpTarget.NonJvm.Native.Mingw.X64.DEFAULT,
        ),
        commonMainSourceSet = {
            dependencies {
                api(project(":request-concept"))
                implementation(deps.kotlin.coroutines.core)
            }
        },
        commonTestSourceSet = {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        },
    )
}

kmpPublish {
    setupModule(
        pomDescription = "Kotlin Components' Request Feature"
    )
}
