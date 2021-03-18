package com.ard.dailyislam.quran.surahList

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ard.dailyislam.quran.repository.SurahRepository
import java.lang.IllegalArgumentException

class SurahListFactory(
    private val setRepository: SurahRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurahListViewModel::class.java))
            return SurahListViewModel(setRepository) as T
        throw IllegalArgumentException()
    }
}