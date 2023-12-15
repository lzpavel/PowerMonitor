package com.lzpavel.powermonitor

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.lzpavel.powermonitor.device.Device
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread


class FloatingWidgetService : Service() {

    val LOG_TAG = "FloatingWidgetService"

    val floatingWidget by lazy {
        FloatingWidget(this)
    }
    var superUserSimple = SuperUserSimple()
    var threadMonitoring = ThreadMonitoring()

    var isStarted = false

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "onCreate")
        ComponentController.floatingWidgetService = this
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LOG_TAG, "onStartCommand")
        isStarted = true
        startForeground(1, FloatingWidgetNotification.build(this))
        floatingWidget.show()
        if (Device.current == Device.ONE_PLUS_7_PRO_LINEAGE) {
            if (superUserSimple.open()) {
                threadMonitoring.start {
                    val v = superUserSimple.readVoltage()
                    val a = superUserSimple.readCurrent()
                    val c = superUserSimple.readCapacity()
                    val str = "$v\n$a\n$c"
                    floatingWidget.postTextValue(str)
                }
            } else {
                Device.current = Device.DEBUG
            }

        } else {
            var cnt = 0
            threadMonitoring.start {
                floatingWidget.postTextValue("${cnt++}")
            }
        }
        ComponentController.mainViewModel?.isStartedFloatingWidgetService = true
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

    fun stopService() {
        threadMonitoring.stop()
        superUserSimple.close()
        floatingWidget.close()
        ComponentController.mainViewModel?.isStartedFloatingWidgetService = false
        if (isStarted) {
            isStarted = false
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }

}