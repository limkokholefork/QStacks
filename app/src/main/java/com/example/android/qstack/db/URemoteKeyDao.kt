package com.example.android.qstack.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.qstack.model.URemoteKey

@Dao
interface URemoteKeyDao {

    @Query("SELECT * FROM uremotekey WHERE questionId = :questionId")
    suspend fun getFRemoteKeysById(questionId: Int): URemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKey(remoteKeyList: List<URemoteKey>)

    @Query("DELETE FROM uremotekey")
    suspend fun nukeURemoteKeyTable()
}