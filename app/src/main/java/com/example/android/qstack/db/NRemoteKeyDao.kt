package com.example.android.qstack.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.android.qstack.model.NRemoteKey

@Dao
interface NRemoteKeyDao {

    @Insert
    suspend fun addAllNRemoteKey(remoteKey: List<NRemoteKey>)

    @Query("SELECT * FROM NRemoteKey WHERE questionId = :questionId")
    suspend fun getRemoteKeyById(questionId : Int) : NRemoteKey?

    @Query("DELETE FROM NRemoteKey")
    suspend fun nukeTable()
}