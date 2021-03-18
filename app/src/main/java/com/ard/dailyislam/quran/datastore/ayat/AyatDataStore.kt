package com.ard.dailyislam.quran.datastore.ayat

import com.ard.dailyislam.quran.model.ayat.Ayat

interface AyatDataStore {
    suspend fun getAyat(id : String) : Ayat?
    suspend fun addAll(id:String ,ayats:Ayat?)
}