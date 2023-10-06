package com.lzpavel.powermonitor

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
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


class MainActivity : ComponentActivity() {



    private lateinit var fwService: FloatingWidgetService
    private var fwBound: Boolean = false

    private lateinit var notificationManager: NotificationManager

    companion object {
        private val none = {}

        var showToastCall = none
        var showWidget = none
        var showWidgetSu = none
        var hideWidget = none
        var showNotification = none
        var showColourPicker = none
        //var showColourPicker2 = MainActivity::showColourPickerFn
    }

    private val fwConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance.
            val binder = service as FloatingWidgetService.FloatingWidgetBinder
            fwService = binder.getService()
            fwBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            fwBound = false
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val LOG_TAG = "TagMainActivity"
        val vm: MainViewModel by viewModels { MainViewModelFactory() }

        Log.d(LOG_TAG, "onCreate")
        showToastCall = {
            var str = "Hello: ${vm.cnt.value}"
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        }
        showWidget = ::showWidget
        showWidgetSu = ::showWidgetSu
        hideWidget = ::hideWidget
        showNotification = ::showNotification
        showColourPicker = ::showColourPickerFn



        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        FloatingWidgetNotification.createNotificationChannel(notificationManager)



        setContent {
            MainView(vm)
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, FloatingWidgetService::class.java).also {intent ->
            bindService(intent, fwConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(fwConnection)
        fwBound = false
    }

    private fun showWidget() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            //val intent: Intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${packageName}"))
            //startActivityForResult(intent, 1)
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                Uri.parse("package:${packageName}")
            })
        } else {
            //fwService.showWidget()
            if (!fwService.isStarted) {
                //startService(Intent(this, FloatingWidgetService::class.java))
                Intent(this, FloatingWidgetService::class.java).apply {
                    putExtra("mode", 0)
                    startService(this)
                }
            }
        }
    }
    private fun showWidgetSu() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            //val intent: Intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${packageName}"))
            //startActivityForResult(intent, 1)
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                Uri.parse("package:${packageName}")
            })
        } else {
            //fwService.showWidget()
            if (!fwService.isStarted) {
                //startService(Intent(this, FloatingWidgetService::class.java))
                Intent(this, FloatingWidgetService::class.java).also {
                    it.putExtra("mode", 1)
                    startService(it)
                }
                //FloatingWidgetService.Mode.MODE_DEBUG
            }
        }
    }

    private fun hideWidget() {
        fwService.closeWidget()
    }

    private fun showNotification() {
        notificationManager.notify(1, FloatingWidgetNotification.build(this))
    }

    private fun showColourPickerFn() {
        startActivity(Intent(this, ColourPickerActivity::class.java))
    }


}



