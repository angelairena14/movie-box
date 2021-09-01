package com.test.moviebox.view

import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.JavascriptInterface
import java.lang.Exception

class KoinGoldJSBridge(val context: Context) {
    @JavascriptInterface
    fun healthCheck(value : String) {
        try {
            context.startActivity(Intent(context,MovieListActivity::class.java).putExtra("passing_value",value).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        } catch (e: Exception){
            Log.i("exceptionn",e.message)
        }
    }
}
