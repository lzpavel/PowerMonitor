package com.lzpavel.powermonitor.storage

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey

object SettingsPreferences {
    val FIELD_COLOR = intPreferencesKey("color")
    val FIELD_SIZE = floatPreferencesKey("size")
}