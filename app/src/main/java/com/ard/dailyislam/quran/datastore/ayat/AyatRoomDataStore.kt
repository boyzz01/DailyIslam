package com.ard.dailyislam.quran.datastore.ayat

import android.util.Log
import com.ard.dailyislam.quran.database.AyatDAO
import com.ard.dailyislam.quran.model.ayat.Ayat

class AyatRoomDataStore(private val ayatDAO: AyatDAO) : AyatDataStore {
    override suspend fun getAyat(id : String): Ayat? {
        val response = ayatDAO.getAyat(id)
        return if (response == null) null else response
    }

    override suspend fun addAll(id: String,ayat: Ayat?) {
        ayat?.let { ayatDAO.insertAll(it) }
    }
}