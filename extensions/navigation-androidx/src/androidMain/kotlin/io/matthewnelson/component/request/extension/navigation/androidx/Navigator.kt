package io.matthewnelson.component.request.extension.navigation.androidx

import androidx.annotation.IdRes
import androidx.navigation.NavController
import io.matthewnelson.component.request.concept.BaseRequestDriver

abstract class Navigator(protected val driver: BaseRequestDriver<NavController>) {

    open suspend fun popBackStack(@IdRes destinationId: Int? = null, inclusive: Boolean = false) {
        driver.submitRequest(PopBackStack(destinationId, inclusive))
    }

    open suspend fun popBackStack(request: PopBackStack) {
        driver.submitRequest(request)
    }

}
