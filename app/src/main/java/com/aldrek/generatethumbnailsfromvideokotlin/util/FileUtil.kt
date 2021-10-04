package com.aldrek.generatethumbnailsfromvideokotlin.util

import android.os.Environment
import java.io.File

class FileUtil {

    companion object{

        fun deleteWholeDirectory(filePath :String){
            val dir = File(Environment.getExternalStorageDirectory().toString() + "$filePath")
            if (dir.isDirectory) {
                val children = dir.list()
                for (i in children.indices) {
                    File(dir, children[i]).delete()
                }
            }
        }
    }


}