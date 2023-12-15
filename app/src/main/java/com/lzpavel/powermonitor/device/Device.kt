package com.lzpavel.powermonitor.device

import com.lzpavel.powermonitor.ComponentController

object Device {
    const val DEBUG = 0
    const val ONE_PLUS_7_PRO_LINEAGE = 1
    var current = DEBUG
        set(value) {
            field = value
            ComponentController.mainViewModel?.deviceType = value
        }
}