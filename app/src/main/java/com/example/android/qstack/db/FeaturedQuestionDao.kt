package com.example.android.qstack.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.qstack.model.FeaturedQuestion

@Dao
interface FeaturedQuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllFeaturedQuestion(featuredQuestionList: List<FeaturedQuestion>?)

    @Query("SELECT * FROM featuredquestion ORDER BY lastActivityDate DESC")
    fun getAllFeaturedQuestions(): PagingSource<Int, FeaturedQuestion>

    @Query("DELETE FROM featuredquestion")
    suspend fun nukeFeaturedQuestion()
}