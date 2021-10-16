import io.matthewnelson.kotlin.components.dependencies.deps
import io.matthewnelson.kotlin.components.dependencies.versions
import io.matthewnelson.kotlin.components.kmp.KmpTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType

plugins {
    id("kmp-configuration")
    id("dependencies")
}

kmpConfiguration {
    setupMultiplatform(
        setOf(

//            KmpTarget.JVM.JVM.DEFAULT,

            KmpTarget.JVM.ANDROID(
                buildTools = versions.buildTools,
                compileSdk = versions.sdkCompile,
                minSdk = versions.sdkMin16,
                mainSourceSet = {
                    dependencies {
                        api(project(":request-concept"))
                        implementation(deps.androidx.navigation.fragment)
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
//                testSourceSet = null,
//            ),
//
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.ALL.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.ARM32.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.ARM64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.X64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.SIMULATOR_ARM64.DEFAULT,
//
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.MACOS.X64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.MACOS.ARM64.DEFAULT,
//
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.TVOS.ALL.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.TVOS.ARM64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.TVOS.X64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.TVOS.SIMULATOR_ARM64.DEFAULT,
//
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.ALL.DEFAULT,
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
        commonMainSourceSet = null,
        commonTestSourceSet = null,
    )
}
