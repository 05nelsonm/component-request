/*
 * Copyright (c) 2021 Matthew Nelson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
import io.matthewnelson.kotlin.components.dependencies.deps
import io.matthewnelson.kotlin.components.dependencies.depsTest
import io.matthewnelson.kotlin.components.dependencies.versions
import io.matthewnelson.kotlin.components.kmp.KmpTarget
import io.matthewnelson.kotlin.components.kmp.KmpTarget.Jvm.Android.Companion.SOURCE_SET_MAIN_NAME as KmpAndroidMain
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType

plugins {
    id("kmp-configuration")
    id("kmp-publish")
}

kmpConfiguration {
    setupMultiplatform(
        setOf(

            KmpTarget.Jvm.Jvm(
                kotlinJvmTarget = JavaVersion.VERSION_1_8,
                testSourceSet = {
                    dependencies {
                        implementation(depsTest.kotlin.coroutines)
                        implementation(kotlin("test-junit"))
                    }
                }
            ),

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
                        implementation(deps.androidx.lifecycle.commonJava8)
                        implementation(deps.androidx.lifecycle.runtime)
                    }
                },
                androidMainSourceSet = {
                    manifest.srcFile("$projectDir/src/$KmpAndroidMain/AndroidManifest.xml")
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
                api(project(":request-concept"))
                implementation(deps.kotlin.coroutines.core.core)
            }
        },
        commonTestSourceSet = {
            dependencies {
                implementation(kotlin("test"))
                implementation(depsTest.kotlin.coroutines)
            }
        },
    )
}

kmpPublish {
    setupModule(
        pomDescription = "Kotlin Components' Request Feature",
    )
}
