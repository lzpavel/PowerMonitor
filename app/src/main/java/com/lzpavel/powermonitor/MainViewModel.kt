package com.lzpavel.powermonitor

import android.content.Context
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {

//    private val _text = MutableLiveData<String>()
//    val text: LiveData<String> = _text
//    val text2: String = "text2"
//    val text3: MutableLiveData<String> = MutableLiveData<String>("Text3")
//    private val _cnt: MutableLiveData<Int> = MutableLiveData(0)
//    val cnt: LiveData<Int> = _cnt
      val cnt: MutableLiveData<Int> = MutableLiveData(0)




    override fun onCleared() {
        super.onCleared()
    }
}