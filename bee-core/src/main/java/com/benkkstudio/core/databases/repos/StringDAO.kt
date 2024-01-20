package com.benkkstudio.core.databases.repos

import android.content.Context
import android.os.Handler
import android.os.Looper
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class StringDAO(
    context: Context,
    rootMode: RootMode = RootMode.LOCAL
) : GRepo(context, rootMode) {
    fun load(fileName: String, default: String = ""): String {
        if (!checkExist(fileName)) return default

        val path = "$root/$fileName"

        val inputStream = FileInputStream(File(path))
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var receiveString: String?
        val stringBuilder = StringBuilder()
        while (bufferedReader.readLine().also { receiveString = it } != null) {
            stringBuilder.append(receiveString)
        }
        inputStream.close()
        return stringBuilder.toString()
    }

    fun loadAsync(filename: String, callback: (data: String) -> Unit) {
        Thread {
            val data = load(filename)
            Handler(Looper.getMainLooper()).post { callback(data) }
        }.start()
    }

    fun save(fileName: String, data: String) {
        val path = "$root/$fileName"
        val outputStreamWriter = OutputStreamWriter(FileOutputStream(File(path)))
        outputStreamWriter.write(data)
        outputStreamWriter.close()
    }

    fun saveAsync(filename: String, data: String, callback: () -> Unit = {}) {
        Thread {
            save(filename, data)
            Handler(Looper.getMainLooper()).post { callback() }
        }.start()
    }
}