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
    var textColorPre: Int = Color.RED

    private fun notifyUpdate() {
        for (u in onUpdate) {
            u.invoke()
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