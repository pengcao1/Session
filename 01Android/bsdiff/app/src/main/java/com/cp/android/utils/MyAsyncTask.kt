package com.cp.android.utils

import android.content.Context
import android.os.AsyncTask
import android.os.Environment
import java.io.File

class MyAsyncTask : AsyncTask<Void, Void, File>() {
    var mContext: Context? = null
    fun MyAsyncTask(context: Context) {
        this.mContext = context
    }

    override fun doInBackground(vararg params: Void?): File {
        if (mContext == null) return File("");

        var oldApkPath = mContext.applicationInfo.sourceDir
        var patchPath = File(Environment.getExternalStorageDirectory(), "patch").absolutePath
        var outputPath = createNewApk().absolutePath
    }

    fun createNewApk(): File {
        var newApk = File(Environment.getExternalStorageDirectory(), "bsdiff.apk")
        if (!newApk.exists()) {
            newApk.createNewFile()
        }
        return newApk
    }

}