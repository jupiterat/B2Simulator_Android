package com.tech.b2simulator.domain.common

enum class ExamScoreType(val score: Int) {
    PASSED(1),
    FAILED(0),
    UNDEFINED(-1);


    companion object {
        const val MAX = 50
        const val PASS_SCORE = 35

        @JvmStatic
        fun fromInt(value: Int) = values().first {
            if (value < PASS_SCORE) {
                return FAILED
            } else {
                return PASSED
            }
        }

        @JvmStatic
        fun isPassed(score: Int): Boolean {
            return fromInt(score) == PASSED
        }
    }
}