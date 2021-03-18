package com.ard.dailyislam.quran.datastore.surah

import com.ard.dailyislam.quran.model.surah.Surah

interface SurahDataStore {
    suspend fun getSurah() : MutableList<Surah>?
    suspend fun addAll(surahs:MutableList<Surah>?)
}