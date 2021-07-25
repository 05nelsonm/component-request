package io.matthewnelson.component.request.sample.activity.main.di

import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.matthewnelson.component.request.master.drivers.CachedRequestDriver
import io.matthewnelson.component.request.master.drivers.RequestDriver
import io.matthewnelson.component.request.sample.screen.a.navigation.ScreenANavigator
import io.matthewnelson.component.request.sample.screen.a.navigation.ScreenBText
import io.matthewnelson.component.request.sample.screen.a.navigation.ScreenCText
import io.matthewnelson.component.request.sample.screen.b.navigation.ScreenBNavigationRequest
import io.matthewnelson.component.request.sample.screen.c.navigation.ScreenCNavigationRequest
import io.matthewnelson.component.request.slave.BaseRequestDriver

@Module
@InstallIn(ActivityRetainedComponent::class)
internal object NavigationModule {

    @Provides
    @ActivityRetainedScoped
    fun provideNavigationDriver(): RequestDriver<NavController> =
        CachedRequestDriver(replayCacheSize = 3)

    @Provides
    fun provideBaseNavigationDriver(
        driver: RequestDriver<NavController>
    ): BaseRequestDriver<NavController> =
        driver

    @Provides
    fun provideScreenANavigator(
        driver: RequestDriver<NavController>
    ): ScreenANavigator<NavController> =
        object : ScreenANavigator<NavController>(driver) {
            override suspend fun toScreenB(text: ScreenBText?) {
                requestDriver.submitRequest(ScreenBNavigationRequest(text?.value))
            }
            override suspend fun toScreenC(text: ScreenCText?) {
                requestDriver.submitRequest(ScreenCNavigationRequest(text?.value))
            }
        }
}
