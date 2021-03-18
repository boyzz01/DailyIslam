package com.ard.dailyislam.quran.ayatlist

import com.ard.dailyislam.quran.model.ayat.Ayat
import java.lang.Exception

data class AyatListViewState (
    val loading :Boolean = false,
    val error: Exception? = null,
    val data: Ayat? =null
)