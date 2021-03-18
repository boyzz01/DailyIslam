package com.ard.dailyislam.quran.datastore.surah

import android.content.Context
import android.content.res.Resources
import com.ard.dailyislam.quran.model.surah.Surah
import com.ard.dailyislam.quran.model.surah.SurahModel
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class SurahRemoteDataStore(private val  context: Context) : SurahDataStore {

    override suspend fun getSurah(): MutableList<Surah>? {
        val jsonString: String = getJsonData(context , "surat")
        try {
            val dataObject = JSONObject(jsonString)
            val surahModel: SurahModel =
                Gson().fromJson(dataObject.toString(), SurahModel::class.java)
            return surahModel.surah

        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }
    }

    override suspend fun addAll(surahs: MutableList<Surah>?) {

    }

    fun getJsonData(context: Context, fileName: String): String {

        val jsonString: String
        try {
            jsonString = context.resources.openRawResource(context.resources.getIdentifier(fileName,
                "raw", context.packageName)).bufferedReader().use {it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return ""
        } catch (e : Resources.NotFoundException ) {
            e.printStackTrace()
            return ""
        }
        return jsonString
    }
}