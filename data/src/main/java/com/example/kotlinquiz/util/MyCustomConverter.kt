package com.example.kotlinquiz.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.niranjan.quiz.modal.AnswerEntity
import org.niranjan.quiz.modal.QuestionEntity
import org.niranjan.quiz.modal.ScoreEntity
import java.lang.reflect.Type


class MyCustomConverter {

            var gson: Gson = Gson()
            @TypeConverter
            fun stringToSomeObjectList(data: String?): List<QuestionEntity> {
                if (data == null) {
                    return emptyList<QuestionEntity>()
                }
                val listType: Type = object : TypeToken<List<QuestionEntity?>?>() {}.getType()
                return gson.fromJson(data, listType)
            }

            @TypeConverter
            fun someScoreListToString(someObjects: List<ScoreEntity?>?): String {
                return gson.toJson(someObjects)
            }

            @TypeConverter
            fun stringToScoreList(data: String?): List<ScoreEntity> {
                if (data == null) {
                    return emptyList<ScoreEntity>()
                }
                val listType: Type = object : TypeToken<List<ScoreEntity?>?>() {}.getType()
                return gson.fromJson(data, listType)
            }

            @TypeConverter
            fun someObjectListToString(someObjects: List<QuestionEntity?>?): String {
                return gson.toJson(someObjects)
            }

            @TypeConverter
            fun someAnswerListToString(someObjects: List<AnswerEntity?>?): String {
                return gson.toJson(someObjects)
            }

            @TypeConverter
            fun stringToAnswerList(data: String?): List<AnswerEntity> {
                if (data == null) {
                    return emptyList<AnswerEntity>()
                }
                val listType: Type = object : TypeToken<List<AnswerEntity?>?>() {}.getType()
                return gson.fromJson(data, listType)
            }
            @TypeConverter
            fun someAnswerToString(someObjects: AnswerEntity?): String {
                return gson.toJson(someObjects)
            }

            @TypeConverter
            fun stringToAnswer(data: String?): AnswerEntity? {
                if (data == null) {
                    return null
                }
                val listType: Type = object : TypeToken<AnswerEntity?>() {}.getType()
                return gson.fromJson(data, listType)
            }
}
