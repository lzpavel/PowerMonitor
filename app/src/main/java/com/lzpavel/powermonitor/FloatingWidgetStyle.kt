package com.lzpavel.powermonitor

import android.graphics.Color
import android.widget.TextView

class FloatingWidgetStyle private constructor() {


    var textColor: Int = Color.BLACK


    companion object {
        private var instance: FloatingWidgetStyle? = null

        fun getInstance() : FloatingWidgetStyle {
            if (instance == null) {
                instance = FloatingWidgetStyle()
            }
            return instance!!
        }
    }



}