package com.lzpavel.powermonitor

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.TextView
//import androidx.lifecycle.ViewTreeLifecycleOwner
//import androidx.lifecycle.ViewTreeViewModelStoreOwner
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread


class FloatingWidgetService : Service() {

    val LOG_TAG = "FloatingWidgetService"

    private val binder = FloatingWidgetBinder()

    //var LAYOUT_FLAG: Int = 0
    var mFloatingView: View? = null
    //var textViewFw: TextView? = null
    lateinit var textViewFw: TextView

    var superUserSession: SuperUserSession? = null


    //lateinit var windowManager: WindowManager;
    val windowManager: WindowManager by lazy {
        getSystemService(WINDOW_SERVICE) as WindowManager
    }

    var isStarted = false
    var isShowing = false
    var cnt: Int = 0

    val FW_MODE_DEBUG = 0
    val FW_MODE_SUPERUSER = 1
    var fwMode = FW_MODE_DEBUG




    inner class FloatingWidgetBinder : Binder() {
        fun getService(): FloatingWidgetService = this@FloatingWidgetService
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LOG_TAG, "onStartCommand")
        isStarted = true
        startForeground(1, FloatingWidgetNotification.build(this))
        //val mode = intent?.getStringExtra("mode")
        val modeRx = intent?.getIntExtra("mode", 0)
        if (modeRx == FW_MODE_SUPERUSER) {
            try {
                superUserSession = SuperUserSession()
                fwMode = FW_MODE_SUPERUSER
            } catch (e: Exception) {
                fwMode = FW_MODE_DEBUG
                e.message?.let {
                    Log.d(LOG_TAG, it)
                }
                e.printStackTrace()
            }
        } else {
            fwMode = FW_MODE_DEBUG
        }
        showWidget()
        tick()

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(LOG_TAG, "onBind")
        return binder
    }

    override fun onRebind(intent: Intent?) {
        Log.d(LOG_TAG, "onRebind")
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(LOG_TAG, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d(LOG_TAG, "onDestroy")
        super.onDestroy()
    }

    fun tick() {
        cnt = 0
        thread {
            runBlocking {
                launch {
                    while (isStarted) {
                        if (fwMode == FW_MODE_SUPERUSER) {
                            val str = superUserSession?.readVoltageCurrent() ?: "null"
                            Log.d(LOG_TAG, str)
                            textViewFw.post {
                                textViewFw.text = str
                            }
                        } else {
                            Log.d(LOG_TAG, "${cnt++}")
                            //textViewFw.text = cnt.toString()
                            textViewFw.post {
                                textViewFw.text = cnt.toString()
                            }
                        }
                        delay(1000)
                    }
                }
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    fun showWidget() {
        if (mFloatingView == null) {
            //LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
            mFloatingView = LayoutInflater.from(this).inflate(R.layout.floating_widget, null)
            textViewFw = mFloatingView!!.findViewById(R.id.textViewFw)
            var layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                //LAYOUT_FLAG,
                //WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
            //layoutParams.gravity = Gravity.TOP|Gravity.RIGHT
            //layoutParams.gravity = Gravity.TOP or Gravity.LEFT
            layoutParams.gravity = Gravity.TOP or Gravity.END
            //layoutParams.gravity = Gravity.CENTER
            layoutParams.x = 0
            layoutParams.y = 0

            var preX = 0F
            var preY = 0F
            var nowX = 0F
            var nowY = 0F
            var deltaX = 0F
            var deltaY = 0F

            //val textViewFw: TextView = mFloatingView!!.findViewById(R.id.textViewFw)

            textViewFw.setOnTouchListener { v, event ->

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

                        windowManager.updateViewLayout(mFloatingView, layoutParams)

                        /*val str = "LayoutX:${layoutParams.x}\nLayoutY:${layoutParams.y}"
                        textViewFw.text = str

                        Log.d(LOG_TAG, "LayoutX:${layoutParams.x}\nLayoutY:${layoutParams.y}")
                        Log.d(LOG_TAG, "EventX:${event.x}\nEventY:${event.y}")
                        Log.d(LOG_TAG, "EventRawX:${event.rawX}\nEventRawY:${event.rawY}")*/

                        return@setOnTouchListener true
                    }
                    else -> {}

                }

                return@setOnTouchListener false
            }

            windowManager.addView(mFloatingView, layoutParams)
            mFloatingView?.visibility = View.VISIBLE
            isShowing = true
            sendBroadcast()
        }
    }

    fun sendBroadcast() {
        Intent().also { intent ->
            intent.setAction(MainActivity.RECEIVER_ACTION)
            intent.putExtra("type", "update")
            intent.putExtra("isShowing", isShowing)
            sendBroadcast(intent)
        }
    }

    fun closeWidget() {
        if (superUserSession != null) {
            superUserSession?.close()
            superUserSession = null
        }
        if (mFloatingView != null) {
            windowManager.removeView(mFloatingView)
            isShowing = false
            sendBroadcast()
            mFloatingView = null
        }
        if (isStarted) {
            isStarted = false
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }



}