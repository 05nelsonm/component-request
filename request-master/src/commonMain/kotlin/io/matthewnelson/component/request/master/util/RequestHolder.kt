package io.matthewnelson.component.request.master.util

import io.matthewnelson.component.request.slave.Request

/**
 * A wrapper for requests that attaches an id to it in order to track
 * execution.
 * */
class RequestHolder<T: Any>(val request: Request<T>) {
    private val id: RequestId = RequestId()

    internal fun getId(): RequestId = id
}
