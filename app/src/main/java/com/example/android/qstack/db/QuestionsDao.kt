package com.example.android.qstack.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.qstack.model.Questions

@Dao
interface QuestionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuestions(question : List<Questions>)

    @Query("SELECT * FROM questions ORDER BY lastActivityDate DESC")
    fun getAllQuestions() : PagingSource<Int, Questions>

    @Query("DELETE FROM questions")
    suspend fun nukeTable()
}