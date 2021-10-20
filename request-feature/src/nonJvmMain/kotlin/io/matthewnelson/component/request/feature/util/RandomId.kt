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

actual value class RandomId private actual constructor(actual val value: String) {

    actual companion object {
        private const val SIZE = 12
        private val CHARS: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        actual operator fun invoke(size: Int): RandomId =
            CharArray(size).let { array ->

                repeat(size) { index ->
                    array[index] = CHARS[Random.nextInt(CHARS.size)]
                }

                RandomId(array.joinToString(""))
            }

        actual operator fun invoke(): RandomId =
            invoke(SIZE)
    }

}
