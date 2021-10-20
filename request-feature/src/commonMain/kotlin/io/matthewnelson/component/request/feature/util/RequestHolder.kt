package io.matthewnelson.component.request.feature.util

import io.matthewnelson.component.request.concept.Request

/**
 * A wrapper for requests that attaches a [RandomId] to it in order to track
 * execution.
 * */
class RequestHolder<T: Any> internal constructor(val request: Request<T>) {

    val id: RandomId = RandomId()

    override fun equals(other: Any?): Boolean {
        return other != null && other is RequestHolder<*> && other.id == id
    }

    override fun hashCode(): Int {
        return 17 * 31 + id.hashCode()
    }

    override fun toString(): String {
        return "RequestHolder(id=$id,request=$request)"
    }

}
