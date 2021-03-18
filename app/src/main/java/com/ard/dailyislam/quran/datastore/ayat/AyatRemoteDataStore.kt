package com.ard.dailyislam.quran.datastore.ayat

import android.content.Context
import android.content.res.Resources
import com.ard.dailyislam.quran.model.ayat.Ayat
import com.ard.dailyislam.quran.model.ayat.AyatModel
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class AyatRemoteDataStore(private val context: Context) : AyatDataStore {

    override suspend fun getAyat(no: String): Ayat? {

        var ayatList : MutableList<AyatModel> = ArrayList()
        var ayat : Ayat?

        val jsonString: String = getJsonData(context, "surat" + no)
        try {
            val dataObject :String = JSONObject(jsonString).getJSONObject("" + no).getString("number_of_ayah")
            val jumlahAyat = Integer.parseInt(dataObject)
            val dataArab = JSONObject(jsonString).getJSONObject("" + no).getJSONObject("text")
            val dataArti = JSONObject(jsonString).getJSONObject("" + no).getJSONObject("translations").getJSONObject("id").getJSONObject("text")
            val dataTafsir = JSONObject(jsonString).getJSONObject(""+no).getJSONObject("tafsir").getJSONObject("id").getJSONObject("kemenag").getJSONObject("text")

            for (i in  1..jumlahAyat) run {
                val arabic: String = dataArab.optString("" + i)
                val arti: String = dataArti.optString("" + i)
                val tafsir: String = dataTafsir.optString("" + i)

                val ayatModel = AyatModel(i,arabic, arti, tafsir)
                ayatList.add(ayatModel)
            }

            ayat = Ayat(no,ayatList)
            return ayat

        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }
    }

    override suspend fun addAll(no: String,ayats: Ayat?) {

    }


    fun getJsonData(context: Context, fileName: String): String {

        val jsonString: String
        try {
            jsonString = context.resources.openRawResource(
                context.resources.getIdentifier(
                    fileName,
                    "raw", context.packageName
                )
            ).bufferedReader().use {it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return ""
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
            return ""
        }
        return jsonString
    }
}