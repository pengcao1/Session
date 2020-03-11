package com.cp.android.bsdiff

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.AsyncTask
import android.os.UserManager
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    var activity: Activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        sample_text.text = stringFromJNI()
        btn_plus.setOnClickListener({ v -> sumResult() })
        generateStoragePermission();
    }

    fun sumResult() {
        var a = edit_plus1.text.toString().toInt()
        var b = edit_plus2.text.toString().toInt()
        result.text = getResultFromJni(a, b).toString()
    }

    fun generateStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var perms: Array<String> = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(perms, 200);
            }
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun getResultFromJni(a: Int, b: Int): Int

    external fun bspatch(oldApkPath: String, newApkPath: String, patchPath: String): Int

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

//    fun update(view: View, context: Context) : Void {
//        var asyncTask = new AsyncTask<Void, Void, File>{}
//    }

//        return new Async

//
//        var mContext = context
//        var mView = view
//        override fun doInBackground(vararg params: Void?): File {
////            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            var oldApkPath = mContext.applicationInfo.sourceDir;
//            var patchPath = File(Environment.getExternalStorageDirectory(),"patch").absolutePath
//            var outputPath = createNewApk().absolutePath
////            return null
////            MainActivity.Companion.
//        }
//
//        override fun onPostExecute(result: File?) {
//            super.onPostExecute(result)
////            UriParselUtils.installApl(activity,result);
//        }
//
//        fun createNewApk():File {
//            var newApk = File(Environment.getExternalStorageDirectory(),"bsdiff.apk")
//            if (!newApk.exists()) {
//                newApk.createNewFile()
//            }
//            return newApk
//        }
//    }

}


