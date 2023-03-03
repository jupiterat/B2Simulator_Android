package com.tech.b2simulator.domain.common


enum class QuestionScoreType(val score: Int) {
    BEST(5),
    GOOD(4),
    MEDIUM(3),
    BAD(2),
    WORST(1),
    ZERO(0),
    UNDEFINED(-1);


    companion object {
        const val MAX = 5

        @JvmStatic
        fun fromInt(value: Int) = values().first { it.score == value }
    }
}