package com.example.architecturepoc2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class SearchViewModel : ViewModel() {
    var count = MutableLiveData(0)

    fun increaseCount() {
        count.value = (count.value ?: 0) + 1
    }
}
