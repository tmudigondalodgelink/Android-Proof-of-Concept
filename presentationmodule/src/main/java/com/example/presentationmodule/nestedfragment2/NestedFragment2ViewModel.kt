package com.example.presentationmodule.nestedfragment2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NestedFragment2ViewModel @Inject constructor() : ViewModel(){
    var count = MutableLiveData(0)

    fun increaseCount() {
        count.value = (count.value ?: 0) + 1
    }
}
