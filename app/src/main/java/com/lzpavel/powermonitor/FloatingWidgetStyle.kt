package com.lzpavel.powermonitor

import android.graphics.Color
import android.widget.TextView

class FloatingWidgetStyle private constructor() {

    val onUpdate = mutableListOf<() -> Unit>()

    var textColor: Int = Color.BLACK
        set(value) {
            field = value
            notifyUpdate()
        }

    var textColorPre: Int = Color.BLACK

    var textSize: Float = 14f
        set(value) {
            field = value
            notifyUpdate()
        }

    private fun notifyUpdate() {
        for (l in onUpdate) {
            l.invoke()
        }
    }

    fun addListener(listener: () -> Unit) {
        onUpdate.add(listener)
    }

    fun removeListener(listener: () -> Unit) {
        onUpdate.remove(listener)
    }

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