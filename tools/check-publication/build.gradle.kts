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
import io.matthewnelson.kotlin.components.kmp.KmpTarget
import io.matthewnelson.kotlin.components.kmp.publish.isSnapshotVersion
import io.matthewnelson.kotlin.components.kmp.publish.kmpPublishRootProjectConfiguration
import io.matthewnelson.kotlin.components.kmp.util.includeSnapshotsRepoIfTrue
import io.matthewnelson.kotlin.components.kmp.util.includeStagingRepoIfTrue
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType

plugins {
    id(pluginId.kmp.configuration)
    id(pluginId.kmp.publish)
}

val pConfig = kmpPublishRootProjectConfiguration!!

includeSnapshotsRepoIfTrue(pConfig.isSnapshotVersion)
includeStagingRepoIfTrue(!pConfig.isSnapshotVersion)

kmpConfiguration {
    setupMultiplatform(targets=
        setOf(
            KmpTarget.Jvm.Jvm.DEFAULT,
            KmpTarget.Jvm.Android(
                buildTools = versions.android.buildTools,
                compileSdk = versions.android.sdkCompile,
                minSdk = versions.android.sdkMin16,
                namespace = "io.matthewnelson.component.tools.check.publication",
                mainSourceSet = {
                    dependencies {
                        implementation("${pConfig.group}:request-extension-navigation-androidx:${pConfig.versionName}")
                    }
                }
            ),
            KmpTarget.NonJvm.JS.DEFAULT,
            KmpTarget.NonJvm.Native.Unix.Linux.X64.DEFAULT,
            KmpTarget.NonJvm.Native.Mingw.X64.DEFAULT,
        ) +
        KmpTarget.NonJvm.Native.Unix.Darwin.Ios.ALL_DEFAULT     +
        KmpTarget.NonJvm.Native.Unix.Darwin.Macos.ALL_DEFAULT   +
        KmpTarget.NonJvm.Native.Unix.Darwin.Tvos.ALL_DEFAULT    +
        KmpTarget.NonJvm.Native.Unix.Darwin.Watchos.ALL_DEFAULT,

        commonMainSourceSet = {
            dependencies {
                implementation("${pConfig.group}:request-concept:${pConfig.versionName}")
                implementation("${pConfig.group}:request-feature:${pConfig.versionName}")
                implementation("${pConfig.group}:request-extension-navigation:${pConfig.versionName}")
            }
        }
    )
}
