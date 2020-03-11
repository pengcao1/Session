package com.cp.android.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

class UriParselUtils {
    companion object {
        fun getFileProvider(context: Context): String {
            return context.applicationInfo.packageName + ".fileprovider";
        }

        fun getUriForFile(context: Context, file: File): Uri {
            return FileProvider.getUriForFile(context, getFileProvider(context), file)
        }

        fun installApl(activity: Activity, apkFile: File) {
            if (!apkFile.exists()) return
            var intent = Intent(Intent.ACTION_VIEW)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                var fileUri = getUriForFile(activity, apkFile)
                intent.setDataAndType(fileUri, "application/vnd.android.package-archive")
            } else {
                intent.setDataAndType(
                    Uri.fromFile(apkFile),
                    "application/vnd.android.package-archive"
                )
            }
            activity.startActivity(intent)
        }
    }
}