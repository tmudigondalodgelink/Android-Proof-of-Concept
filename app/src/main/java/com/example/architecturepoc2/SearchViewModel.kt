package com.example.architecturepoc2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    var count = MutableLiveData(0)

    fun increaseCount() {
        count.value = (count.value ?: 0) + 1
    }
}
