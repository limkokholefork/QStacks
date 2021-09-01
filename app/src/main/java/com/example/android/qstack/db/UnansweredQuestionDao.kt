package com.example.android.qstack.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.qstack.model.UnansweredQuestion

@Dao
interface UnansweredQuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUnansweredQuestions(question : List<UnansweredQuestion>)

    @Query("SELECT * FROM unansweredquestion ORDER BY lastActivityDate DESC")
    fun getAllQuestions() : PagingSource<Int, UnansweredQuestion>

    @Query("DELETE FROM unansweredquestion")
    suspend fun nukeTable()
}