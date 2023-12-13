package com.lzpavel.powermonitor

import android.util.Log
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class SuperUserSingle private constructor(){

    private lateinit var process: Process
    private lateinit var reader: BufferedReader
    private lateinit var readerError: BufferedReader
    private lateinit var writer: BufferedWriter

    companion object {
        private const val LOG_TAG = "SuperUserSingle"
        private var instance: SuperUserSingle? = null
        const val STATE_ERROR = -1
        const val STATE_CLOSED = 0
        const val STATE_OPENED = 1
        var state: Int = STATE_CLOSED
        //var isOpened = false
        //var countConnections = 0

        fun open() {
            if (instance == null && state == STATE_CLOSED) {
                try {
                    instance = SuperUserSingle()
                    instance!!.openSu()
                    state = STATE_OPENED
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d(LOG_TAG, "Error open super user session")
                    state = STATE_ERROR
                    instance = null
                }

            }
        }

        fun close() {
            if (instance != null && state == STATE_OPENED) {
                instance!!.closeSu()
                instance = null
                state = STATE_CLOSED
            }
        }

        fun readVoltage() : String {
            if (instance != null && state == STATE_OPENED) {
                return instance!!.readVoltageSu()
            }
            return ""
        }

        /*fun connectAndGet() : SuperUserSingle? {
            if (countConnections >= 0) {
                if (countConnections == 0) {
                    open()
                }
                countConnections++
                return instance!!
            }
            return null
        }
        fun disconnect() {
            if (countConnections > 0) {
                if (countConnections == 1) {
                    close()
                }
                countConnections--
            }
        }*/
    }

    private fun readVoltageSu() : String {
        val path = """/sys/class/power_supply/battery/voltage_now"""
        writer.write("cat $path\n")
        writer.flush()
        /*while (!reader.ready()) {
            Thread.sleep(100)
        }*/
        val result = reader.readLine() ?: ""
        return result
    }

    private fun execute(cmd: String) {
        writer.write("$cmd\n")
        writer.flush()
    }

    private fun openSu() {
        process = Runtime.getRuntime().exec("su")
        reader = BufferedReader(InputStreamReader(process.inputStream))
        readerError = BufferedReader(InputStreamReader(process.errorStream))
        writer = BufferedWriter(OutputStreamWriter(process.outputStream))
    }

    private fun closeSu() {
        writer.write("exit\n")
        writer.flush()
        process.waitFor()
    }
}