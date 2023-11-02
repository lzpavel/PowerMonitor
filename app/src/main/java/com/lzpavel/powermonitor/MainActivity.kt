package com.lzpavel.powermonitor

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.lzpavel.powermonitor.views.MainView
import kotlin.system.exitProcess


class MainActivity : ComponentActivity() {

    val LOG_TAG = "MainActivity"

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }

    //private lateinit var fwService: FloatingWidgetService
    //private var isFwConnected: Boolean = false

    private lateinit var notificationManager: NotificationManager

    /*companion object {
        const val RECEIVER_ACTION = "com.lzpavel.powermonitor.MAIN_ACTIVITY_RECEIVER"
    }*/

    /*private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d(LOG_TAG, "BroadcastReceiver: onReceive")
            val type: String = intent?.getStringExtra("type") ?: ""
            if (type == "update") {
                val state: Boolean = intent?.getBooleanExtra("isShowing", false) ?: false
                viewModel.updateFloatingWidgetShowing(state)
                Log.d(LOG_TAG, "BroadcastReceiver: onReceive: isShowing: $state")
            }
        }

    }*/

    /*private val fwConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance.
            val binder = service as FloatingWidgetService.FloatingWidgetBinder
            fwService = binder.getService()
            isFwConnected = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isFwConnected = false
        }
    }*/

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate")

        /*viewModel.floatingWidgetStyleLive.observe(this, Observer {
            if (isFwConnected) {
                fwService.floatingWidget?.updateTextStyle()
            }
        })*/

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        FloatingWidgetNotification.createNotificationChannel(notificationManager)

        setContent {
            MainView(
                viewModel,
                onButtonExit = {
                    finishAffinity()
                    exitProcess(0)
                },
                onClickFloatingWidgetSwitcher = {
                    if (!FloatingWidgetService.isStarted) {
                        startFloatingWidgetService()
                    } else {
                        FloatingWidgetService.stop()
                    }
                }
            )
        }
    }

    override fun onStart() {
        super.onStart()
        /*subscribeReceiver()
        Intent(this, FloatingWidgetService::class.java).also {intent ->
            bindService(intent, fwConnection, Context.BIND_AUTO_CREATE)
        }*/
    }

    override fun onStop() {
        super.onStop()
        //unbindService(fwConnection)
        //unsubscribeReceiver()
        //isFwConnected = false
    }

    /*@SuppressLint("UnspecifiedRegisterReceiverFlag")
    fun subscribeReceiver() {
        //val br: BroadcastReceiver = MyBroadcastReceiver()

        val filter = IntentFilter(RECEIVER_ACTION)
        if (Build.VERSION.SDK_INT >= 33) {
            val receiverFlags = RECEIVER_NOT_EXPORTED
            registerReceiver(broadcastReceiver, filter, receiverFlags)
        } else {
            //registerReceiver(br, filter, 0)
            registerReceiver(broadcastReceiver, filter)
        }
    }*/

    /*fun unsubscribeReceiver() {
        unregisterReceiver(broadcastReceiver)
    }*/

    private fun startFloatingWidgetService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            //val intent: Intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${packageName}"))
            //startActivityForResult(intent, 1)
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                Uri.parse("package:${packageName}")
            })
        } else {
            startService(Intent(this, FloatingWidgetService::class.java))
            //fwService.showWidget()
                /*Intent(this, FloatingWidgetService::class.java).apply {
                    putExtra("mode", 0)
                    startService(this)
                }*/

        }
    }

    /*private fun stopFloatingWidgetService() {
        FloatingWidgetService.
    }*/

}



