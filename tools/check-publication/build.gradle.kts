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
import io.matthewnelson.kotlin.components.dependencies.versions
import io.matthewnelson.kotlin.components.kmp.KmpTarget
import io.matthewnelson.kotlin.components.kmp.publish.kmpPublishRootProjectConfiguration
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType

plugins {
    id("dependencies")
    id("kmp-configuration")
    id("kmp-publish")
}

kmpPublishRootProjectConfiguration?.let { config ->
    repositories {
        if (config.versionName.endsWith("-SNAPSHOT")) {
            maven("https://oss.sonatype.org/content/repositories/snapshots/")
        } else {
            maven("https://oss.sonatype.org/content/groups/staging") {
                credentials {
                    username = rootProject.ext.get("mavenCentralUsername").toString()
                    password = rootProject.ext.get("mavenCentralPassword").toString()
                }
            }
        }
    }
}

kmpConfiguration {
    setupMultiplatform(
        setOf(

            KmpTarget.Jvm.Jvm.DEFAULT,

            KmpTarget.Jvm.Android(
                buildTools = versions.android.buildTools,
                compileSdk = versions.android.sdkCompile,
                minSdk = versions.android.sdkMin16,
                mainSourceSet = {
                    dependencies {
                        project.kmpPublishRootProjectConfiguration?.let { config ->
                            dependencies {
                                implementation("${config.group}:request-extension-navigation-androidx:${config.versionName}")
                            }
                        }
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
                testSourceSet = null,
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
            project.kmpPublishRootProjectConfiguration?.let { config ->
                dependencies {
                    implementation("${config.group}:request-concept:${config.versionName}")
                    implementation("${config.group}:request-feature:${config.versionName}")
                    implementation("${config.group}:request-extension-navigation:${config.versionName}")
                }
            }
        }
    )
}
