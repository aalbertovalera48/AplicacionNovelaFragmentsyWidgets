package com.example.aplicacionnovela.ui.theme

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object FileHelper {

    fun writeToFile(context: Context, fileName: String, data: String, useExternal: Boolean) {
        val file: File = if (useExternal) {
            File(context.getExternalFilesDir(null), fileName)
        } else {
            File(context.filesDir, fileName)
        }

        try {
            FileOutputStream(file).use { fos ->
                fos.write(data.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun readFromFile(context: Context, fileName: String, useExternal: Boolean): String? {
        val file: File = if (useExternal) {
            File(context.getExternalFilesDir(null), fileName)
        } else {
            File(context.filesDir, fileName)
        }

        return try {
            file.readText()
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}