package com.example.android.qstack.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.qstack.model.NewQuestion

@Dao
interface NewQuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNEwQuestions(questionList: List<NewQuestion>)

    @Query("SELECT * FROM NewQuestion ORDER BY creationDate DESC")
    fun getAllNewQuestion() : PagingSource<Int, NewQuestion>

    @Query("DELETE FROM NewQuestion")
    suspend fun nukeNewQuestionTable()
}