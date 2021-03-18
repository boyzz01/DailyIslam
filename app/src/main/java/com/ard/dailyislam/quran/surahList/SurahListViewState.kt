package com.ard.dailyislam.quran.surahList

import com.ard.dailyislam.quran.model.surah.Surah
import java.lang.Exception

data class SurahListViewState (
    val loading :Boolean = false,
    val error: Exception? = null,
    val data: MutableList<Surah>? = null
)