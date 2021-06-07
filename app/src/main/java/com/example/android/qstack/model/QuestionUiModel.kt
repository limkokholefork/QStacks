package com.example.android.qstack.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question")
data class QuestionUiModel(@PrimaryKey val id : Int,
                           val link : String,
                           val title : String,
                           val scores : Int,
                           val answers : Int,
                           val views : Int,
                           val creationDate : Long,
                           val tags : List<String>,
                           @Embedded
                           val owner : QuestionUiOwner)

data class QuestionUiOwner(val profileImageLink : String?,
                           val userId : Int?,
                           val ownerLink : String?,
                           val ownerName : String)
