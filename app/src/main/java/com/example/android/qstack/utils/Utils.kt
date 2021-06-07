package com.example.android.qstack.utils

import com.example.android.qstack.model.QuestionUiModel
import com.example.android.qstack.model.QuestionResponse
import com.example.android.qstack.model.QuestionUiOwner

enum class OrderBY(val order : String){
    DESC("desc"), ASC("asc")
}

enum class SortBy(val sortOrder : String){
    ACTIVITY("activity"), CREATION("creation"), VOTES("votes")
}

fun QuestionResponse.extractQuestionUiModel() : List<QuestionUiModel>?{
    return this.items?.map {
        QuestionUiModel(id = it.questionId,
            link = it.link,
            title = it.title,
            scores = it.score ?: -1,
            answers = it.answerCount ?: -1,
            views = it.viewCount ?: -1,
            creationDate = it.creationDate,
            tags = it.tags ?: emptyList(),
            owner = QuestionUiOwner(profileImageLink = it.owner.profileImage,
                userId = it.owner.userId,
                ownerLink = it.owner.link,
                ownerName = it.owner.displayName)
        )
    }
}