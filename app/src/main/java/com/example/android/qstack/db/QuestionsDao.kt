package com.example.android.qstack.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.android.qstack.model.QuestionUiModel

@Dao
interface QuestionsDao {

    @Insert
    fun addQuestions(questionUiModels : List<QuestionUiModel>)

    @Query("SELECT * FROM question")
    fun getAllQuestions() : PagingSource<Int, QuestionUiModel>

    @Query("DELETE FROM question")
    fun nukeTable()
}