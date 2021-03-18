package com.ard.dailyislam.quran.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ard.dailyislam.quran.model.ayat.Ayat

@Dao
interface AyatDAO {
    @Query("SELECT * FROM Ayat WHERE `id` = :id")
    suspend fun getAyat(id: String): Ayat

    @Insert
    suspend fun insertAll(vararg ayat: Ayat)


}