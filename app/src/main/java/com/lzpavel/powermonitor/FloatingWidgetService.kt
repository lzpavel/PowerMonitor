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

    //private val binder = FloatingWidgetBinder()



    var floatingWidget: FloatingWidget? = null

    var superUserSession: SuperUserSession? = null


    //var isStarted = false
    var isShowing = false
    var cnt: Int = 0

    val FW_MODE_DEBUG = 0
    val FW_MODE_SUPERUSER = 1
    var fwMode = FW_MODE_DEBUG


    companion object {
        private var instance: FloatingWidgetService? = null
        var onChangeStarted: (() -> Unit)? = null
        var isStarted = false
            private set(value) {
                field = value
                onChangeStarted?.invoke()
            }

        fun stop() {
            if (isStarted) {
                instance?.stopServicePrivate()
            }
        }

        /*fun getInstance() : FloatingWidgetService {
            if (instance == null) {
                instance = FloatingWidgetService()
            }
            return instance!!
        }*/
    }

    /*inner class FloatingWidgetBinder : Binder() {
        fun getService(): FloatingWidgetService = this@FloatingWidgetService
    }*/

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "onCreate")
        instance = this
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
        floatingWidget = FloatingWidget(this)
        floatingWidget?.show()
        isShowing = true
        //sendBroadcast()
        tick()

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
        instance = null
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

    /*fun sendBroadcast() {
        Intent().also { intent ->
            intent.setAction(MainActivity.RECEIVER_ACTION)
            intent.putExtra("type", "update")
            intent.putExtra("isShowing", isShowing)
            sendBroadcast(intent)
        }
    }*/

    private fun stopServicePrivate() {
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
        if (isStarted) {
            isStarted = false
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }



}