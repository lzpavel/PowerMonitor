package com.lzpavel.powermonitor

import android.graphics.Color
import android.widget.TextView

object FloatingWidgetStyle {
    var preTextColor: Int = Color.BLACK
        set(value) {
            field = value
            ComponentController.floatingWidgetService?.floatingWidget?.preTextColor = value
            ComponentController.mainViewModel?.textColorPreFloatingWidget = value
        }
    var textColor: Int = Color.BLACK
        set(value) {
            field = value
            ComponentController.floatingWidgetService?.floatingWidget?.textColor = value
            ComponentController.mainViewModel?.textColorFloatingWidget = value
        }
    var textSize: Float = 16F
        set(value) {
            field = value
            ComponentController.floatingWidgetService?.floatingWidget?.textSize = value
            ComponentController.mainViewModel?.textSizeFloatingWidget = value
        }
}