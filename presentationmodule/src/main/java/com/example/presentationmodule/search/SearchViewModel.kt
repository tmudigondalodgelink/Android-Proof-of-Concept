package com.example.presentationmodule.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel(){
    var count = MutableLiveData(0)

    fun increaseCount() {
        count.value = (count.value ?: 0) + 1
    }
}
