package com.example.android.qstack.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.qstack.model.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remotekey WHERE questionId = :keyId")
    suspend fun getRemoteKeyById(keyId: Int) : RemoteKey?

    @Query("DELETE FROM remotekey")
    suspend fun nukeRemoteKeyTable()
}