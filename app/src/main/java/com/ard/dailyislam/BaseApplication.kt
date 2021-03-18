package com.ard.dailyislam

import android.app.Application
import com.ard.dailyislam.quran.database.QuranDatabase
import com.ard.dailyislam.quran.datastore.ayat.AyatRemoteDataStore
import com.ard.dailyislam.quran.datastore.ayat.AyatRoomDataStore
import com.ard.dailyislam.quran.datastore.surah.SurahRemoteDataStore
import com.ard.dailyislam.quran.datastore.surah.SurahRoomDataStore
import com.ard.dailyislam.quran.repository.AyatRepository
import com.ard.dailyislam.quran.repository.SurahRepository

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val appDatabase = QuranDatabase.getInstance(this)
        SurahRepository.instance.apply {
            init(
                SurahRoomDataStore(appDatabase.SurahDAO()),
                SurahRemoteDataStore(this@BaseApplication)
            )
        }

        AyatRepository.instance.apply {
            init(
                AyatRoomDataStore(appDatabase.AyatDAO()),
                AyatRemoteDataStore(this@BaseApplication)
            )
        }

    }
}