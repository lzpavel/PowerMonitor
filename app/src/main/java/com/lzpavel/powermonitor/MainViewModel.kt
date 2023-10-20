package com.lzpavel.powermonitor

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.graphics.Color
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
    val mainActivityExecute: MutableLiveData<Int> = MutableLiveData(0)
    private val _isFloatingWidgetShowing: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFloatingWidgetShowing: LiveData<Boolean> = _isFloatingWidgetShowing
    private val _floatingWidgetColor: MutableLiveData<Int> = MutableLiveData(0x000000FF)
    val floatingWidgetColor: LiveData<Int> = _floatingWidgetColor


    fun updateFloatingWidgetColor(colorArgb: Int) {
        _floatingWidgetColor.value = colorArgb
    }

    fun updateFloatingWidgetShowing(isShowing: Boolean) {
        _isFloatingWidgetShowing.value = isShowing
    }


    override fun onCleared() {
        super.onCleared()
    }
}