package com.lzpavel.powermonitor

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //return super.create(modelClass)
        return if (ComponentController.floatingWidgetService != null) {
            MainViewModel(
                ComponentController.floatingWidgetService!!.isStarted,
                ComponentController.floatingWidgetService!!.floatingWidget!!.textColor,
                ComponentController.floatingWidgetService!!.floatingWidget!!.textSize

            ) as T
        } else {
            MainViewModel() as T
        }
        //return MainViewModel() as T
    }
}