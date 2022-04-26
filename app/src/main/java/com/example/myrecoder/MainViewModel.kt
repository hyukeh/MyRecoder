package com.example.myrecoder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val count:MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    init {
        count.value=0
    }

    fun getCount(): LiveData<Int> { return count }

    fun addCount() { count.value = count.value?.plus(1) }
}