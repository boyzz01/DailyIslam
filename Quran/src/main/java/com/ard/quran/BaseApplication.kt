package com.ard.quran

import android.app.Application
import com.ard.quran.database.QuranDatabase
import com.ard.quran.datastore.ayat.AyatRemoteDataStore
import com.ard.quran.datastore.ayat.AyatRoomDataStore
import com.ard.quran.datastore.surah.SurahRemoteDataStore
import com.ard.quran.datastore.surah.SurahRoomDataStore
import com.ard.quran.repository.AyatRepository
import com.ard.quran.repository.SurahRepository


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