package com.test.moviebox.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.test.moviebox.R
import com.test.testlib.nacl

class WebViewActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val secretMessage = "lorem ipsum"
        val messageBytes = secretMessage.toByteArray()
        val clientPublic = Base64.decode(
            "eud6PaZW8RX2dSw/u1ABm33ejXpFwwNfaK5G4HgJzSY=",
            Base64.DEFAULT
        )
        val clientSecret = Base64.decode(
            "KSh9+nmD9pYpb34G+NDgzib671tDEc3bPJPaOh5pnqw=",
            Base64.DEFAULT
        )
        val serverPublic = Base64.decode(
            "Gl2qkNi+0Sh1rb/lhWLf1KgvTD7ta4SXQjORn2SNLiY=",
            Base64.DEFAULT
        )
        val serverSecret = Base64.decode(
            "LlTyzJxYArSONA4UlPsodbNdDOeM4xGHQO/jlmhTVRo=",
            Base64.DEFAULT
        )
        val nonce = ByteArray(nacl.crypto_box_NONCEBYTES)
        val box = nacl.box.seal(messageBytes,nonce, clientSecret, serverPublic)
        Log.i("box_is",Gson().toJson(box))
        val opened = nacl.box.open(messageBytes,nonce ,serverSecret,clientPublic)?:""
        Log.i("opened_is","hi ${opened.toString().length}")

//        webview.settings.javaScriptEnabled = true
//        webview.addJavascriptInterface(KoinGoldJSBridge(applicationContext), "KoinGoldBridge")
//        webview.settings.domStorageEnabled = true
//        webview.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                view?.loadUrl(url)
//                return true
//            }
//        }
//        webview.loadUrl("https://6f6297f632df.ngrok.io/marketplace?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MjgxNjQ0NzAsImlhdCI6MTYyODA3ODA3MCwiaXNzIjoiYXNnYXJkLXVzZXIiLCJqdGkiOiI5MTkwZTZjYmE0NGI0NGJkYWViOThlYjJiMzdlNmM2MyIsInVhdCI6IjY4NWRlM2Q3NjA4YjQ5NjE0NzI0OGUyMjZmMDlhOTA0TTJJMllqSXlNR0l0TWpBeE9TMDBNamM1TFRnek5ETXRZMkpsTmpFMk4yWTNOakF5IiwidWNkIjoiNjg1ZGUzZDc2MDhiNDk2MTQ3MjQ4ZTIyNmYwOWE5MDRWRVV3TURBd01nPT0iLCJ1ZW0iOiI2ODVkZTNkNzYwOGI0OTYxNDcyNDhlMjI2ZjA5YTkwNFlXNW5aV3hoTG1seVpXNWhLM0psWm1GamRHOXlNVUJyYjJsdWQyOXlhM011WTI5dCIsInVpZCI6IjY4NWRlM2Q3NjA4YjQ5NjE0NzI0OGUyMjZmMDlhOTA0TVRRek5qTTNPQT09IiwidW5xIjoiNjg1ZGUzZDc2MDhiNDk2MTQ3MjQ4ZTIyNmYwOWE5MDRNMlpqTWprNE1ETXRObU5oTVMwME5UQmpMV0ZtTW1RdE9HWmxaakEyTlRneU1XRTEifQ.ABxecKb6AmOT7uP4sQW9c802jMh3fCIW0LUDoVEJrJE")
    }
}