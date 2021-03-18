package com.ard.dailyislam.quran.datastore.surah

import com.ard.dailyislam.quran.database.SurahDAO
import com.ard.dailyislam.quran.model.surah.Surah

class SurahRoomDataStore(private val surahDAO:SurahDAO) : SurahDataStore {
    override suspend fun getSurah(): MutableList<Surah>? {
        val response = surahDAO.getAll()
        return if (response.isEmpty()) null else response
    }

    override suspend fun addAll(surahs: MutableList<Surah>?) {
        surahs?.let { surahDAO.insertAll(*it.toTypedArray() ) }
    }
}