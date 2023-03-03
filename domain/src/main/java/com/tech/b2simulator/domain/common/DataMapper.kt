package com.tech.b2simulator.domain.common

import com.tech.b2simulator.domain.model.ExamInfo
import com.tech.b2simulator.domain.model.QuestionInfo

fun QuestionInfo.getQuestionScoreType(): QuestionScoreType {
    return QuestionScoreType.fromInt(score)
}

fun ExamInfo.getExamScoreType(): ExamScoreType {
    return ExamScoreType.fromInt(score)
}