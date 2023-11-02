package com.lzpavel.powermonitor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.TextView

@SuppressLint("ClickableViewAccessibility")
class FloatingWidget(context: Context) {

    private val mainView: View = LayoutInflater.from(context).inflate(R.layout.floating_widget, null)
    private var textView: TextView = mainView.findViewById(R.id.textViewFw)
    private val windowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private val layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        //LAYOUT_FLAG,
        //WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
    )

    //private var textColor: Int = Color.BLACK
    private val floatingWidgetStyle: FloatingWidgetStyle = FloatingWidgetStyle.getInstance()
    private val onUpdateFloatingWidgetStyle: () -> Unit = {
        textView.setTextColor(floatingWidgetStyle.textColor)
        //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, floatingWidgetStyle.textSize)
        textView.textSize = floatingWidgetStyle.textSize
    }

    private var preX = 0F
    private var preY = 0F
    private var nowX = 0F
    private var nowY = 0F
    private var deltaX = 0F
    private var deltaY = 0F


    init {
        layoutParams.gravity = Gravity.TOP or Gravity.END
        layoutParams.x = 0
        layoutParams.y = 0

        textView.setOnTouchListener { _, event ->

            nowX = event.rawX
            nowY = event.rawY

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {

                    preX = nowX
                    preY = nowY

                    return@setOnTouchListener true
                }

                MotionEvent.ACTION_UP -> {}
                MotionEvent.ACTION_MOVE -> {

                    deltaX = nowX - preX
                    deltaY = nowY - preY

                    layoutParams.x -= deltaX.toInt()
                    layoutParams.y += deltaY.toInt()

                    preX = nowX
                    preY = nowY

                    windowManager.updateViewLayout(mainView, layoutParams)

                    return@setOnTouchListener true
                }
                else -> {}

            }

            return@setOnTouchListener false
        }
        onUpdateFloatingWidgetStyle.invoke()
        floatingWidgetStyle.addListener(onUpdateFloatingWidgetStyle)
    }
    /*init {
        updateTextStyle()
    }*/

    fun setTextValue(text: String) {
        textView.text = text
    }

    fun postTextValue(text: String) {
        mainView.post {
            textView.text = text
        }
    }

    /*fun updateTextStyle() {
        textView.setTextColor(floatingWidgetStyle.textColor)
    }*/

    /*fun setTextColor(color: Int) {
        //textColor = color
        //textView.setTextColor(color)
        textView.setTextColor(floatingWidgetStyle.textColor)
    }*/
    /*fun getTextColor() : Int {
        return textColor
    }*/

    fun show() {
        windowManager.addView(mainView, layoutParams)
        mainView.visibility = View.VISIBLE
    }

    fun close() {
        floatingWidgetStyle.removeListener(onUpdateFloatingWidgetStyle)
        windowManager.removeView(mainView)
    }

}