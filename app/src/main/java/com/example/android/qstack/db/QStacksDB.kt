package com.example.android.qstack.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.android.qstack.model.QuestionUiModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType

@TypeConverters(TagsTypeConverter::class)
@Database(entities = [QuestionUiModel::class], exportSchema = false, version = 1)
abstract class QStacksDB : RoomDatabase() {
    abstract fun getQuestionDao(): QuestionsDao
}

class TagsTypeConverter {

    private val moshi: Moshi = Moshi.Builder().build()
    val type: ParameterizedType = Types.newParameterizedType(List::class.java, String::class.java)
    private val adapter: JsonAdapter<List<String>> = moshi.adapter(type)

    @TypeConverter
    fun convertFromJsonToStringList(jsonString: String?): List<String>? {
        return jsonString?.let {
            adapter.fromJson(it)
        }
    }

    @TypeConverter
    fun convertFromListToJson(questionList: List<String>?): String {
        return adapter.toJson(questionList)
    }
}