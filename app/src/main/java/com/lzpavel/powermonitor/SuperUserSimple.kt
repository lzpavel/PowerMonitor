package com.lzpavel.powermonitor

import android.util.Log
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class SuperUserSimple {

    val LOG_TAG = "SuperUserSimple"

    private lateinit var process: Process
    private lateinit var reader: BufferedReader
    private lateinit var readerError: BufferedReader
    private lateinit var writer: BufferedWriter

    var isOpened = false

    fun open() : Boolean {
        try {
            process = Runtime.getRuntime().exec("su")
            reader = BufferedReader(InputStreamReader(process.inputStream))
            readerError = BufferedReader(InputStreamReader(process.errorStream))
            writer = BufferedWriter(OutputStreamWriter(process.outputStream))
            isOpened = true
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(LOG_TAG, "Error open super user session")
            isOpened = false
        }
        return isOpened
    }

    fun readVoltage() : String {
        var result = "-"
        if (isOpened) {
            val path = """/sys/class/power_supply/battery/voltage_now"""
            writer.write("cat $path\n")
            writer.flush()
            result = reader.readLine()
        }
        return result
    }
    fun readCurrent() : String {
        var result = "-"
        if (isOpened) {
            val path = """/sys/class/power_supply/battery/current_now"""
            writer.write("cat $path\n")
            writer.flush()
            result = reader.readLine()
        }
        return result
    }

    fun readCapacity() : String {
        var result = "-"
        if (isOpened) {
            val path = """/sys/class/power_supply/battery/charge_counter"""
            writer.write("cat $path\n")
            writer.flush()
            result = reader.readLine()
        }
        return result
    }

    fun close() {
        if (isOpened) {
            writer.write("exit\n")
            writer.flush()
            process.waitFor()
            isOpened = false
        }
    }

}