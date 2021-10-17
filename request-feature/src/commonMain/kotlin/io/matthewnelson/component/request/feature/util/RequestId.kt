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

import kotlin.random.Random

internal class RequestId private constructor(val value: String) {

    companion object {
        private val CHARS: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        private const val SIZE: Int = 12

        operator fun invoke(size: Int = SIZE): RequestId =
            CharArray(size).let { array ->

                repeat(size) { index ->
                    array[index] = CHARS[Random.nextInt(CHARS.size)]
                }

                RequestId(array.joinToString(""))
            }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        return other is RequestId && other.value == value
    }

    override fun hashCode(): Int {
        var result = 17
        result = result * 31 + value.hashCode()
        return result
    }

    override fun toString(): String {
        return "RequestId(value=$value)"
    }

}