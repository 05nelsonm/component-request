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
import io.matthewnelson.kotlin.components.dependencies.versions
import io.matthewnelson.kotlin.components.kmp.KmpTarget
import io.matthewnelson.kotlin.components.kmp.KmpTarget.Jvm.Android.Companion.SOURCE_SET_MAIN_NAME as KmpAndroidMain

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
                kotlinJvmTarget = JavaVersion.VERSION_1_8,
                compileSourceOption = JavaVersion.VERSION_1_8,
                compileTargetOption = JavaVersion.VERSION_1_8,
                target = {
                    publishLibraryVariants("release")
                },
                mainSourceSet = {
                    dependencies {
                        api(project(":extensions:request-extension-navigation"))
                        implementation(deps.androidx.navigation.fragment)
                    }
                },
                androidMainSourceSet = {
                    manifest.srcFile("$projectDir/src/$KmpAndroidMain/AndroidManifest.xml")
                    res.setSrcDirs(listOf("$projectDir/src/$KmpAndroidMain/res"))
                }
            )
        )
    )
}

kmpPublish {
    setupModule(
        pomDescription = "Kotlin Components' Request Concept extension for androidx navigation",
    )
}
