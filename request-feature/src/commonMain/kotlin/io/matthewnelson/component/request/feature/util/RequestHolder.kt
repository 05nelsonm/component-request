/*
*  Copyright 2021 Matthew Nelson
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
* */
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
