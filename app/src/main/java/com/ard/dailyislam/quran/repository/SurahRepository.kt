package com.ard.dailyislam.quran.repository

import androidx.lifecycle.LiveData
import com.ard.dailyislam.quran.database.SurahDAO
import com.ard.dailyislam.quran.datastore.surah.SurahDataStore
import com.ard.dailyislam.quran.model.surah.Surah

class SurahRepository private constructor(): BaseRepository<SurahDataStore>() {




    suspend fun getSurah():MutableList<Surah>?{
        val cache = localDataStore?.getSurah()

        if (cache !=null) return cache
        val response = remoteDataStore?.getSurah()
        localDataStore?.addAll(response)
        return response
    }

    companion object {
        val instance by lazy { SurahRepository() }
    }

}