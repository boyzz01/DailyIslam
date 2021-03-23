package com.ard.quran.datastore.surah

import com.ard.quran.model.surah.Surah

interface SurahDataStore {
    suspend fun getSurah() : MutableList<Surah>?
    suspend fun addAll(surahs:MutableList<Surah>?)
}