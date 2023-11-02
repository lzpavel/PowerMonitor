package com.lzpavel.powermonitor

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

    var floatingWidgetStyle: FloatingWidgetStyle = FloatingWidgetStyle.getInstance()

    val cnt: MutableLiveData<Int> = MutableLiveData(0)
    val mainActivityExecute: MutableLiveData<Int> = MutableLiveData(0)
    private val _isFloatingWidgetShowing: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFloatingWidgetShowing: LiveData<Boolean> = _isFloatingWidgetShowing



    private val _textColorFloatingWidgetLive: MutableLiveData<Int> = MutableLiveData(floatingWidgetStyle.textColor)
    val textColorFloatingWidgetLive: LiveData<Int> = _textColorFloatingWidgetLive
    private val _textSizeFloatingWidgetLive: MutableLiveData<Float> = MutableLiveData(floatingWidgetStyle.textSize)
    val textSizeFloatingWidgetLive: LiveData<Float> = _textSizeFloatingWidgetLive

    private val onUpdateFloatingWidgetStyle: () -> Unit = {
        _textColorFloatingWidgetLive.value = floatingWidgetStyle.textColor
        _textSizeFloatingWidgetLive.value = floatingWidgetStyle.textSize
    }


    init {
        floatingWidgetStyle.addListener(onUpdateFloatingWidgetStyle)
    }






    fun updateFloatingWidgetShowing(isShowing: Boolean) {
        _isFloatingWidgetShowing.value = isShowing
    }


    override fun onCleared() {
        super.onCleared()
        floatingWidgetStyle.removeListener(onUpdateFloatingWidgetStyle)
    }
}