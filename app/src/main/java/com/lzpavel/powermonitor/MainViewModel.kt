package com.lzpavel.powermonitor

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lzpavel.powermonitor.device.Device

class MainViewModel(
    isStartedFloatingWidgetService: Boolean = false,
    textColorFloatingWidget: Int = FloatingWidgetStyle.textColor,
    textSizeFloatingWidget: Float = FloatingWidgetStyle.textSize,
    deviceType: Int = Device.current
) : ViewModel() {

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

    var deviceType: Int = deviceType
        set(value) {
            field = value
            _deviceTypeLive.value = value
        }
    private val _deviceTypeLive: MutableLiveData<Int> = MutableLiveData(deviceType)
    val deviceTypeLive: LiveData<Int> = _deviceTypeLive


    init {
        ComponentController.mainViewModel = this
    }

    override fun onCleared() {
        ComponentController.mainViewModel = null
        super.onCleared()
    }
}