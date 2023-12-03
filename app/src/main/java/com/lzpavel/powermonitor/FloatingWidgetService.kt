package com.lzpavel.powermonitor

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread


class FloatingWidgetService : Service() {

    val LOG_TAG = "FloatingWidgetService"

    var floatingWidget: FloatingWidget? = null

    var superUserSession: SuperUserSession? = null

    var isStarted = false
    var isShowing = false
    var cnt: Int = 0

    val FW_MODE_DEBUG = 0
    val FW_MODE_SUPERUSER = 1
    var fwMode = FW_MODE_DEBUG

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "onCreate")
        ComponentController.floatingWidgetService = this
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LOG_TAG, "onStartCommand")
        isStarted = true
        startForeground(1, FloatingWidgetNotification.build(this))
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
        floatingWidget = if (ComponentController.mainViewModel != null) {
            FloatingWidget(
                this,
                ComponentController.mainViewModel!!.textColorFloatingWidget,
                ComponentController.mainViewModel!!.textSizeFloatingWidget
            )
        } else {
            FloatingWidget(this)
        }
        floatingWidget?.show()
        isShowing = true
        tick()
        if (ComponentController.mainViewModel != null) {
            ComponentController.mainViewModel!!.isStartedFloatingWidgetService = true
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(LOG_TAG, "onBind")
        //return binder
        return Binder()
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
        ComponentController.floatingWidgetService = null
        //instance = null
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
                            /*textViewFw.post {
                                textViewFw.text = str
                            }*/
                            floatingWidget?.postTextValue(str)
                        } else {
                            Log.d(LOG_TAG, "${cnt++}")
                            //textViewFw.text = cnt.toString()
                            /*textViewFw.post {
                                textViewFw.text = cnt.toString()
                            }*/
                            floatingWidget?.postTextValue(cnt.toString())
                        }
                        delay(1000)
                    }
                }
            }
        }

    }

    fun stopService() {
        if (superUserSession != null) {
            superUserSession?.close()
            superUserSession = null
        }
        if (floatingWidget != null) {
            floatingWidget?.close()
            floatingWidget = null
            isShowing = false
            //sendBroadcast()
        }
        if (ComponentController.mainViewModel != null) {
            ComponentController.mainViewModel!!.isStartedFloatingWidgetService = false
        }
        if (isStarted) {
            isStarted = false
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }

}