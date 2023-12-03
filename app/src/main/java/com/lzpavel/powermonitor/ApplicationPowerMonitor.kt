package com.lzpavel.powermonitor

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class ApplicationPowerMonitor : Application() {
    override fun onCreate() {
        super.onCreate()
        //val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    }
}