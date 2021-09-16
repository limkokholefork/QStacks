package com.example.android.qstack.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.android.qstack.model.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType

@TypeConverters(TagsTypeConverter::class)
@Database(entities = [Questions::class,
    RemoteKey::class,
    NewQuestion::class,
    NRemoteKey::class,
    FeaturedQuestion::class,
    FRemoteKey::class,
    URemoteKey::class,
    UnansweredQuestion::class,
    TagDbModel::class],
    exportSchema = false, version = 1)
abstract class QStacksDB : RoomDatabase() {
    abstract fun getQuestionDao(): QuestionsDao
    abstract fun getRemoteKeyDao() : RemoteKeyDao
    abstract fun getNewQuestionDao() : NewQuestionDao
    abstract fun getNRemoteKeyDao() : NRemoteKeyDao
    abstract fun getFeaturedQuestionDao(): FeaturedQuestionDao
    abstract fun getFRemoteKeyDao(): FRemoteKeyDao
    abstract fun getURemoteKeyDao(): URemoteKeyDao
    abstract fun getUnansweredQuestion(): UnansweredQuestionDao
    abstract fun getTagsDao(): TagDbDao
}

class TagsTypeConverter {

    private val moshi: Moshi = Moshi.Builder().build()
    private val type: ParameterizedType = Types.newParameterizedType(List::class.java, String::class.java)
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