package com.ard.dailyislam.quran.repository

import com.ard.dailyislam.quran.datastore.ayat.AyatDataStore
import com.ard.dailyislam.quran.model.ayat.Ayat

class AyatRepository private constructor(): BaseRepository<AyatDataStore>() {
    suspend fun getAyat(no : String):Ayat?{
        val cache = localDataStore?.getAyat(no)
        if (cache !=null) return cache
        val response = remoteDataStore?.getAyat(no)
        localDataStore?.addAll(no,response)
        return response
    }

    companion object {
        val instance by lazy { AyatRepository() }
    }
}