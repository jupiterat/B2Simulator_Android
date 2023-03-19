package com.tech.b2simulator.data.mapper

import com.tech.b2simulator.data.local.entity.CategoryAction
import com.tech.b2simulator.data.local.entity.CategoryLocation
import com.tech.b2simulator.data.local.entity.Exams
import com.tech.b2simulator.data.local.entity.Questions
import com.tech.b2simulator.domain.model.CategoryActionInfo
import com.tech.b2simulator.domain.model.CategoryLocationInfo
import com.tech.b2simulator.domain.model.ExamInfo
import com.tech.b2simulator.domain.model.QuestionInfo

fun CategoryAction.toCategoryActionInfo(): CategoryActionInfo {
    return CategoryActionInfo(id, title, hint)
}


fun CategoryLocation.toCategoryLocationInfo(): CategoryLocationInfo {
    return CategoryLocationInfo(id, title, 0, totalInExam = totalInExam)
}

fun Questions.toQuestionInfo(): QuestionInfo {
    return QuestionInfo(
        id,
        title,
        description,
        hint,
        url,
        validTime,
        thumbnail,
        groupByLocation,
        groupByAction,
        saved == 1, score
    )
}

fun QuestionInfo.toQuestionEntity(): Questions {
    return Questions(
        id,
        title,
        description,
        hint,
        url,
        validTime,
        thumbnail,
        groupByLocation,
        groupByAction,
        if (saved) 1 else 0,
        score
    )
}

fun Exams.toExamInfo(): ExamInfo {
    return ExamInfo(id, title, score)
}

fun ExamInfo.toExam(): Exams {
    return Exams(id, title, score)
}