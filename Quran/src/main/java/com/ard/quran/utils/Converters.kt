package com.ard.quran.utils

import androidx.room.TypeConverter
import com.ard.quran.model.ayat.AyatModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<AyatModel> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<AyatModel?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<AyatModel?>?): String {
        return Gson().toJson(someObjects)
    }
}