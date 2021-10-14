package com.aldrek.generatethumbnailsfromvideokotlin.util

import android.content.Context
import android.os.Environment
import com.aldrek.generatethumbnailsfromvideokotlin.activity.MainActivity
import java.io.File
import java.io.IOException

class FileUtil {

    companion object {

        fun deleteWholeDirectory( filePath: String) {
            deleteFiles("${Environment.getExternalStorageDirectory().absolutePath}$filePath")

        }

        fun deleteFiles(path: String) {
            val file = File(path)
            if (file.exists()) {
                val deleteCmd = "rm -r $path"
                val runtime = Runtime.getRuntime()
                try {
                    runtime.exec(deleteCmd)
                } catch (e: IOException) {
                }
            }
        }


    }

}