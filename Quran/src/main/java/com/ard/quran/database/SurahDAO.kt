package com.ard.quran.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ard.quran.model.ayat.Ayat
import com.ard.quran.model.surah.Surah

@Dao
interface SurahDAO {

    @Query("SELECT * FROM Surah")
    suspend fun getAll():MutableList<Surah>

    @Insert
    suspend fun insertAll(vararg surahs: Surah)

}