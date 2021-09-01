package com.example.android.qstack.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class RemoteKey(@PrimaryKey val questionId : Int,
                     val prevKey : Int?,
                     val nextKey : Int?)

@Entity
data class NRemoteKey(@PrimaryKey val questionId : Int,
                     val prevKey : Int?,
                     val nextKey : Int?)

@Entity
data class FRemoteKey(@PrimaryKey val questionId: Int,
                      val prevKey: Int?,
                      val nextKey: Int?)

@Entity
data class URemoteKey(@PrimaryKey val questionId: Int,
                      val prevKey: Int?,
                      val nextKey: Int?)
