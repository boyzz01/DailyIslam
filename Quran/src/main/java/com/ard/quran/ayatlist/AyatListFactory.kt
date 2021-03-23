package com.ard.quran.ayatlist

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ard.quran.repository.AyatRepository
import com.ard.quran.repository.SurahRepository
import java.lang.IllegalArgumentException

class AyatListFactory(
    private val setRepository: AyatRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AyatListViewModel::class.java))
            return AyatListViewModel(setRepository) as T
        throw IllegalArgumentException()
    }
}