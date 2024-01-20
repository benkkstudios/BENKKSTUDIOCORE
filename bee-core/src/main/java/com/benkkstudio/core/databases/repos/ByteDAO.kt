package com.benkkstudio.core.databases.repos

import android.content.Context
import android.os.Handler
import android.os.Looper
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class ByteDAO(
    context: Context,
    mode: RootMode
) : GRepo(context, mode) {

    fun load(filename: String): ByteArray? {
        val path = "$root/$filename"
        val size = File(path).length().toInt()
        val bytes = ByteArray(size)
        try {
            val buf = BufferedInputStream(FileInputStream(path))
            buf.read(bytes, 0, bytes.size)
            buf.close()
            return bytes
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun loadAsync(filename: String, callback: (bytes: ByteArray?) -> Unit) {
        Thread {
            val bytes = load(filename)
            Handler(Looper.getMainLooper()).post { callback(bytes) }
        }.start()
    }

    fun save(filename: String, bytes: ByteArray) {
        val path = "$root/$filename"
        val out = FileOutputStream(path)
        out.write(bytes)
        out.flush()
        out.close()
    }

    fun saveAsync(filename: String, bytes: ByteArray, callback: () -> Unit = {}) {
        Thread {
            save(filename, bytes)
            Handler(Looper.getMainLooper()).post { callback() }
        }.start()
    }
}