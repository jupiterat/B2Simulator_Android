package com.tech.b2simulator.common

import com.tech.b2simulator.R
import com.tech.b2simulator.domain.common.ExamScoreType
import com.tech.b2simulator.domain.common.QuestionScoreType

fun getQuestionScoreColor(score: Int): Int {
    return when (QuestionScoreType.fromInt(score)) {
        QuestionScoreType.BEST -> {
            R.color.green
        }
        QuestionScoreType.GOOD -> {
            R.color.green
        }
        QuestionScoreType.MEDIUM -> {
            R.color.darkYellow
        }
        QuestionScoreType.BAD -> {
            R.color.orange
        }
        QuestionScoreType.WORST -> {
            R.color.red
        }
        else -> {
            R.color.red
        }
    }
}

fun getExamScoreColor(score: Int): Int {
    return when (ExamScoreType.fromInt(score)) {
        ExamScoreType.PASSED -> {
            R.color.green
        }
        ExamScoreType.FAILED -> {
            R.color.red
        }
        ExamScoreType.UNDEFINED -> {
            R.color.green
        }
    }
}

fun getExamScoreText(score: Int): Int {
    return when (ExamScoreType.fromInt(score)) {
        ExamScoreType.PASSED -> {
            R.string.passed
        }
        ExamScoreType.FAILED -> {
            R.string.failed
        }
        ExamScoreType.UNDEFINED -> {
            R.string.failed
        }
    }
}

fun getExamScoreBg(score: Int): Int {
    return when (ExamScoreType.fromInt(score)) {
        ExamScoreType.PASSED -> {
            R.drawable.green_rounded_bg
        }
        ExamScoreType.FAILED -> {
            R.drawable.red_rounded_bg
        }
        ExamScoreType.UNDEFINED -> {
            R.drawable.green_rounded_bg
        }
    }
}