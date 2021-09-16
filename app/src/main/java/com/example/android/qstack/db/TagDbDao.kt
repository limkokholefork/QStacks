package com.example.android.qstack.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.android.qstack.model.TagDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDbDao {

    @Insert
    fun saveAllTags(tagDbModelList: List<TagDbModel>?)

    @Query("SELECT * FROM tagdbmodel")
    fun getAllTags(): Flow<List<TagDbModel>>

    @Query("DELETE FROM tagdbmodel")
    fun nukeTagTable()
}