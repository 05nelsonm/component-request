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
