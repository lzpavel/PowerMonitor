package com.lzpavel.powermonitor

import android.graphics.Color
import androidx.datastore.dataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.lzpavel.powermonitor.storage.dataStore

class MainViewModel(
    isStartedFloatingWidgetService: Boolean = false,
    textColorFloatingWidget: Int = Color.BLACK,
    textSizeFloatingWidget: Float = 14F
) : ViewModel() {

//    private val _text = MutableLiveData<String>()
//    val text: LiveData<String> = _text
//    val text2: String = "text2"
//    val text3: MutableLiveData<String> = MutableLiveData<String>("Text3")
//    private val _cnt: MutableLiveData<Int> = MutableLiveData(0)
//    val cnt: LiveData<Int> = _cnt

    var isStartedFloatingWidgetService = isStartedFloatingWidgetService
        set(value) {
            field = value
            _isStartedFloatingWidgetServiceLive.value = value
        }
    private val _isStartedFloatingWidgetServiceLive: MutableLiveData<Boolean> = MutableLiveData(isStartedFloatingWidgetService)
    val isStartedFloatingWidgetServiceLive: LiveData<Boolean> = _isStartedFloatingWidgetServiceLive

    var textColorFloatingWidget: Int = textColorFloatingWidget
        set(value) {
            field = value
            _textColorFloatingWidgetLive.value = value
        }
    private val _textColorFloatingWidgetLive: MutableLiveData<Int> = MutableLiveData(textColorFloatingWidget)
    val textColorFloatingWidgetLive: LiveData<Int> = _textColorFloatingWidgetLive
    var textColorPreFloatingWidget: Int = textColorFloatingWidget

    var textSizeFloatingWidget: Float = textSizeFloatingWidget
        set(value) {
            field = value
            _textSizeFloatingWidgetLive.value = value
        }
    private val _textSizeFloatingWidgetLive: MutableLiveData<Float> = MutableLiveData(textSizeFloatingWidget)
    val textSizeFloatingWidgetLive: LiveData<Float> = _textSizeFloatingWidgetLive


    //var floatingWidgetStyle: FloatingWidgetStyle = FloatingWidgetStyle.getInstance()

    //val cnt: MutableLiveData<Int> = MutableLiveData(0)
    //val mainActivityExecute: MutableLiveData<Int> = MutableLiveData(0)
    //private val _isFloatingWidgetShowing: MutableLiveData<Boolean> = MutableLiveData(false)
    //val isFloatingWidgetShowing: LiveData<Boolean> = _isFloatingWidgetShowing



    /*private val onUpdateServiceStarted = {
        _isServiceStarted.value = FloatingWidgetService.isStarted
    }*/




    /*private val onUpdateFloatingWidgetStyle: () -> Unit = {
        _textColorFloatingWidgetLive.value = floatingWidgetStyle.textColor
        _textSizeFloatingWidgetLive.value = floatingWidgetStyle.textSize
    }*/


    init {
        ComponentController.mainViewModel = this
        //FloatingWidgetService.onChangeStarted = onUpdateServiceStarted
        //floatingWidgetStyle.addListener(onUpdateFloatingWidgetStyle)
        /*viewModelScope.launch {
            dataStore.data
        }*/
    }






    /*fun updateFloatingWidgetShowing(isShowing: Boolean) {
        _isFloatingWidgetShowing.value = isShowing
    }*/


    override fun onCleared() {
        ComponentController.mainViewModel = null
        super.onCleared()

        //FloatingWidgetService.onChangeStarted = null
        //floatingWidgetStyle.removeListener(onUpdateFloatingWidgetStyle)
    }
}