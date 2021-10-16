package io.matthewnelson.component.request.sample.screen.a.navigation

import io.matthewnelson.component.request.concept.BaseRequestDriver

abstract class ScreenANavigator<Controller: Any>(
    protected val requestDriver: BaseRequestDriver<Controller>
) {
    abstract suspend fun toScreenB(text: ScreenBText?)
    abstract suspend fun toScreenC(text: ScreenCText?)
}
