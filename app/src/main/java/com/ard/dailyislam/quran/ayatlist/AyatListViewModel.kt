package com.ard.dailyislam.quran.ayatlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ard.dailyislam.quran.repository.AyatRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class AyatListViewModel(
    private val ayatRepository: AyatRepository
) : ViewModel(){
    private val mViewState = MutableLiveData<AyatListViewState>().apply {
        value = AyatListViewState(loading = true)
    }
    val viewState : LiveData<AyatListViewState>
        get() = mViewState



    fun getAyats(no : String) = viewModelScope.launch {

        try {
            val data = ayatRepository.getAyat(no)
            mViewState.value = mViewState.value?.copy(loading = false,error = null,data = data)
        } catch (ex: Exception){
            mViewState.value = mViewState.value?.copy(loading = false,error = ex,data = null)
            Log.d("ayat","disini ayat"+ex.toString())
        }

    }
}