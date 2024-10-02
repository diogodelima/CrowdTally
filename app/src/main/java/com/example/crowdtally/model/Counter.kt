package com.example.crowdtally.model

import kotlin.math.max
import kotlin.math.min

data class  Counter(

    val current: Int = 10,
    val max: Int = 20

) {

    init {

        require(max > 0) { "max value should be positive" }
        require(current in 0..max) { "current should be greater than 0 and lower than $max" }

    }

    operator fun inc() = copy(current = min(current + 1, max))

    operator fun dec() = copy(current = max(current - 1, 0))

    fun canIncrease() = current < max

    fun canDecrease() = current > 0

}