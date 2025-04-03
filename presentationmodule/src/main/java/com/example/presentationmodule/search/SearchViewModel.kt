package com.example.presentationmodule.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

interface ISearchViewModel {
    var count: MutableLiveData<Int>
    fun increaseCount()
}

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel(), ISearchViewModel {
    override var count = MutableLiveData(0)

    override fun increaseCount() {
        count.value = (count.value ?: 0) + 1
    }
}
