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
import io.matthewnelson.kotlin.components.kmp.util.kapt

plugins {
    id(pluginId.kmp.configuration)
}

kmpConfiguration {
    setupMultiplatform(targets=
        setOf(
            KmpTarget.Jvm.Android(
                buildTools = versions.android.buildTools,
                compileSdk = versions.android.sdkCompile,
                minSdk = versions.android.sdkMin16,
                namespace = "io.matthewnelson.component.request.sample.activity.main",
                pluginIds = setOf(pluginId.kotlin.kapt, pluginId.google.hilt),
                androidConfig = {
                    buildFeatures.viewBinding = true
                },
                mainSourceSet = {
                    dependencies handler@ {
                        api(deps.androidx.appCompat)
                        implementation(deps.androidx.constraintLayout)
                        implementation(deps.viewBindingDelegateNoReflect)
                        implementation(deps.google.hilt)
                        kapt(project, depsKapt.google.hilt)
                    }
                },
            ),
        ),

        commonMainSourceSet = {
            dependencies {
                api(project(":request-feature"))
                api(project(":samples:screens:screen-a"))
                api(project(":samples:screens:screen-b"))
                api(project(":samples:screens:screen-c"))
            }
        },
    )
}
