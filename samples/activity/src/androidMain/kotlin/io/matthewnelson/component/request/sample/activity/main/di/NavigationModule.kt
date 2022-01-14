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
package io.matthewnelson.component.request.sample.activity.main.di

import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.matthewnelson.component.request.feature.drivers.CachedRequestDriver
import io.matthewnelson.component.request.feature.drivers.RequestDriver
import io.matthewnelson.component.request.sample.screen.a.navigation.ScreenANavigator
import io.matthewnelson.component.request.sample.screen.a.navigation.ScreenBText
import io.matthewnelson.component.request.sample.screen.a.navigation.ScreenCText
import io.matthewnelson.component.request.sample.screen.b.navigation.ScreenBNavigationRequest
import io.matthewnelson.component.request.sample.screen.c.navigation.ScreenCNavigationRequest
import io.matthewnelson.component.request.extension.navigation.Navigator

@Module
@InstallIn(ActivityRetainedComponent::class)
internal object NavigationModule {

    @Provides
    @ActivityRetainedScoped
    fun provideNavigationDriver(): RequestDriver<NavController> =
        CachedRequestDriver(replayCacheSize = 3)

    @Provides
    fun provideScreenANavigator(
        driver: RequestDriver<NavController>
    ): ScreenANavigator<NavController> =
        object : ScreenANavigator<NavController>(driver) {
            override suspend fun toScreenB(text: ScreenBText?) {
                driver.submitRequest(ScreenBNavigationRequest(text?.value))
            }
            override suspend fun toScreenC(text: ScreenCText?) {
                driver.submitRequest(ScreenCNavigationRequest(text?.value))
            }
        }

    @Provides
    fun provideNavigator(
        screenANavigator: ScreenANavigator<NavController>
    ): Navigator<NavController> =
        screenANavigator
}
