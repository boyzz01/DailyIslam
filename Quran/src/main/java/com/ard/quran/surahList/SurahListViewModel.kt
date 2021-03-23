package com.ard.quran.surahList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ard.quran.model.surah.Surah
import com.ard.quran.repository.SurahRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class SurahListViewModel(
    private val surahRepository: SurahRepository
) : ViewModel(){
    private val mViewState = MutableLiveData<SurahListViewState>().apply {
        value = SurahListViewState(loading = true)

    }
    val viewState : LiveData<SurahListViewState>
        get() = mViewState

    init {
        getSurahs()
    }

    fun getSurahs() = viewModelScope.launch {
        try {
            val data = surahRepository.getSurah()
            mViewState.value = mViewState.value?.copy(loading = false,error = null,data = data)
        } catch (ex: Exception){
            mViewState.value = mViewState.value?.copy(loading = false,error = ex,data = null)
        }
    }

}