import io.matthewnelson.components.dependencies.Versions
import io.matthewnelson.components.kmp.KmpTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType

plugins {
    id("kmp-configuration")
    id("dependencies")
}

kmpConfiguration {
    setupMultiplatform(
        setOf(

            KmpTarget.JVM.JVM.DEFAULT,

            KmpTarget.JVM.ANDROID(
                buildTools = Versions.buildTools,
                compileSdk = Versions.compileSdk,
                minSdk = Versions.minSdk16,
                targetSdk = Versions.compileSdk,
            ),

            KmpTarget.NON_JVM.JS(
                compilerType = KotlinJsCompilerType.BOTH,
                browser = KmpTarget.NON_JVM.JS.Browser(
                    jsBrowserDsl = null
                ),
                node = KmpTarget.NON_JVM.JS.Node(
                    jsNodeDsl = null
                ),
                mainSourceSet = null,
                testSourceSet =  null,
            ),

            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.ALL.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.ARM32.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.ARM64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.X64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.SIMULATOR_ARM64.DEFAULT,

            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.MACOS.X64.DEFAULT,
            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.MACOS.ARM64.DEFAULT,

            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.TVOS.ALL.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.TVOS.ARM64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.TVOS.X64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.TVOS.SIMULATOR_ARM64.DEFAULT,

            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.ALL.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.ARM32.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.ARM64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.X64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.X86.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.SIMULATOR_ARM64.DEFAULT,

//            KmpTarget.NON_JVM.NATIVE.UNIX.LINUX.ARM32HFP.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.LINUX.MIPS32.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.UNIX.LINUX.MIPSEL32.DEFAULT,
            KmpTarget.NON_JVM.NATIVE.UNIX.LINUX.X64.DEFAULT,

            KmpTarget.NON_JVM.NATIVE.MINGW.X64.DEFAULT,
//            KmpTarget.NON_JVM.NATIVE.MINGW.X86.DEFAULT,
        ),
        commonMainSourceSet = null,
        commonTestSourceSet = {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        },
    )
}
