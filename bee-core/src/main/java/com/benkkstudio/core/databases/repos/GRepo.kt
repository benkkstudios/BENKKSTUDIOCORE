package com.benkkstudio.core.databases.repos

import android.content.Context
import android.os.Environment
import java.io.File

enum class RootMode {
    LOCAL,
    CACHE,
    EXTERNAL
}

abstract class GRepo(context: Context, mode: RootMode) {

    protected var root: File = when (mode) {
        RootMode.LOCAL -> context.filesDir
        RootMode.CACHE -> context.cacheDir
        RootMode.EXTERNAL -> Environment.getExternalStorageDirectory()
    }

    fun checkExist(fileName: String) = File("$root/$fileName").exists()

    /**
     * @param filename be careful about user files in external storage mode
     */
    fun remove(filename: String) {
        val f = File("$root/$filename")
        if (f.exists()) f.delete()
    }
}