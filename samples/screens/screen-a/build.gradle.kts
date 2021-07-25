import io.matthewnelson.components.dependencies.Deps
import io.matthewnelson.components.dependencies.KaptDeps
import io.matthewnelson.components.dependencies.Versions
import io.matthewnelson.components.kmp.KmpTarget
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency

plugins {
    id("kmp-configuration")
    id("dependencies")
}

kmpConfiguration {
    setupMultiplatform(
        setOf(

//            KmpTarget.JVM.JVM.DEFAULT,

            KmpTarget.JVM.ANDROID(
                buildTools = Versions.buildTools,
                compileSdk = Versions.compileSdk,
                minSdk = Versions.minSdk16,
                targetSdk = Versions.compileSdk,
                pluginIds = setOf("dagger.hilt.android.plugin", "kotlin-kapt"),
                androidConfig = {
                    buildFeatures.viewBinding = true
                },
                mainSourceSet = {
                    dependencies {
                        api(Deps.androidx.navigation.fragment)
                        implementation(Deps.viewBindingDelegateNoReflect)
                        implementation(Deps.androidx.constraintLayout)
                        implementation(Deps.google.hilt)

                        implementation(KaptDeps.google.hilt.hilt)
                        configurations.getByName(KaptDeps.kapt).dependencies.add(
                            DefaultExternalModuleDependency(
                                KaptDeps.google.hilt.group,
                                KaptDeps.google.hilt.name,
                                KaptDeps.google.hilt.version,
                            )
                        )
                    }
                }
            ),

//            KmpTarget.NON_JVM.JS(
//                compilerType = KotlinJsCompilerType.BOTH,
//                browser = KmpTarget.NON_JVM.JS.Browser(
//                    jsBrowserDsl = null
//                ),
//                node = KmpTarget.NON_JVM.JS.Node(
//                    jsNodeDsl = null
//                ),
//                mainSourceSet = null,
//                testSourceSet = null
//            ),
//
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.ARM32.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.ARM64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.X64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.SIMULATOR_ARM64.DEFAULT,
//
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.MACOS.X64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.MACOS.ARM64.DEFAULT,
//
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.TVOS.ARM64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.TVOS.X64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.TVOS.SIMULATOR_ARM64.DEFAULT,
//
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.ARM32.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.ARM64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.X64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.X86.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.SIMULATOR_ARM64.DEFAULT,
//
//            KmpTarget.NON_JVM.NATIVE.UNIX.LINUX.ARM32HFP.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.LINUX.MIPS32.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.LINUX.MIPSEL32.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.LINUX.X64.DEFAULT,
//
//            KmpTarget.NON_JVM.NATIVE.MINGW.X64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.MINGW.X86.DEFAULT,
        ),

        commonMainSourceSet = {
            dependencies {
                api(project(":request-slave"))
            }
        },
        commonTestSourceSet = null
    )
}
