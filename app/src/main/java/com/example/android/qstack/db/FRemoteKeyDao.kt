package com.example.android.qstack.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.qstack.model.FRemoteKey

@Dao
interface FRemoteKeyDao {

    @Query("SELECT * FROM fremotekey WHERE questionId = :questionId")
    suspend fun getFRemoteKeysById(questionId: Int): FRemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKey(remoteKeyList: List<FRemoteKey>)

    @Query("DELETE FROM fremotekey")
    suspend fun nukeFRemoteKeyTable()
}