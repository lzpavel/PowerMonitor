package com.lzpavel.powermonitor

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class ThreadMonitoring {

    var isStarted = false

    var onTick: () -> Unit = {}

    fun start(onTickNew: (() -> Unit)? = null) {
        onTickNew?.let {
            onTick = it
        }
        if (!isStarted) {
            isStarted = true
            thread {
                runBlocking {
                    launch {
                        while (isStarted) {
                            onTick()
                            delay(1000)
                        }
                    }
                }
            }
        }
    }

    fun stop() {
        isStarted = false
    }

}