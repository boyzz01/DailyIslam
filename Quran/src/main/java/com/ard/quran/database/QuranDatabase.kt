package com.ard.quran.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ard.quran.model.surah.Surah
import com.ard.quran.model.ayat.Ayat
import com.ard.quran.utils.Converters

@Database(entities = [Surah::class,Ayat::class ],version = 1,exportSchema = false )
@TypeConverters(Converters::class)
abstract class QuranDatabase : RoomDatabase(){
    abstract fun SurahDAO():SurahDAO
    abstract fun AyatDAO():AyatDAO

    companion object{
        private var instance:QuranDatabase? = null
        fun getInstance(context: Context): QuranDatabase{
            instance?.let { return it }
            instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuranDatabase::class.java,
                    "Quran"
            ).build()
            return instance!!
        }
    }
}