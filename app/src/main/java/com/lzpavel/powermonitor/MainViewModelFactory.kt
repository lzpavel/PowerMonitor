package com.lzpavel.powermonitor

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //return super.create(modelClass)
        return MainViewModel(

        ) as T
    }
}