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
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.lzpavel.powermonitor.device.Device
import com.lzpavel.powermonitor.storage.SettingsPreferences
import com.lzpavel.powermonitor.storage.dataStore
import com.lzpavel.powermonitor.views.MainView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess


class MainActivity : ComponentActivity() {

    val LOG_TAG = "MainActivity"

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }

    private lateinit var notificationManager: NotificationManager

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate")

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        FloatingWidgetNotification.createNotificationChannel(notificationManager)

        lifecycleScope.launch {
            dataStore.data.collect {prefs ->
                prefs[SettingsPreferences.FIELD_COLOR]?.let {
                    FloatingWidgetStyle.textColor = it
                }
                prefs[SettingsPreferences.FIELD_SIZE]?.let {
                    FloatingWidgetStyle.textSize = it
                }
                prefs[SettingsPreferences.FIELD_DEVICE]?.let {
                    Device.current = it
                }
            }
        }

        setContent {
            MainView(viewModel)
        }

        ComponentController.mainActivity = this
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        ComponentController.mainActivity = null
        super.onDestroy()
    }

    fun exitApp() {
        finishAffinity()
        exitProcess(0)
    }

    fun testSu() {
        val su = SuperUserSimple()
        if (su.isOpened){
            Log.d(LOG_TAG, "V:${su.readVoltage()}")
            Log.d(LOG_TAG, "A:${su.readCurrent()}")
            su.close()
        }
    }

    fun switchService() {
        if (ComponentController.floatingWidgetService == null) {
            startFloatingWidgetService()
        } else {
            ComponentController.floatingWidgetService!!.stopService()
        }
    }

    fun saveSettings() {
        runBlocking {
            dataStore.edit { prefs ->
                prefs[SettingsPreferences.FIELD_COLOR] = FloatingWidgetStyle.textColor
                prefs[SettingsPreferences.FIELD_SIZE] = FloatingWidgetStyle.textSize
                prefs[SettingsPreferences.FIELD_DEVICE] = Device.current
            }
        }

    }

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


}



