package com.example.android.qstack.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class RemoteKey(@PrimaryKey val questionId : Int,
                     val prevKey : Int?,
                     val nextKey : Int?)
