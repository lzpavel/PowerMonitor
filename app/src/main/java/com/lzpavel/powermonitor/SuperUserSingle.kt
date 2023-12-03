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
        var isOpened = false
        var countConnections = 0

        private fun open() {
            if (instance == null) {
                try {
                    instance = SuperUserSingle()
                    instance!!.openSu()
                    isOpened = true
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d(LOG_TAG, "Error open super user session")
                    isOpened = false
                    instance = null
                }

            }
        }

        private fun close() {
            if (instance != null) {
                instance!!.closeSu()
                instance = null
                isOpened = false
            }
        }

        fun connectAndGet() : SuperUserSingle? {
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
        }
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
        process.waitFor();
    }
}