package com.test.moviebox.utils

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

class HeaderInterceptor (var context: Context?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var requestBuilder = request.newBuilder()
        request = requestBuilder.build()

        var response= chain.proceed(request)
        var stringData = ""
        if (response.code == 200 || response.code == 201){
            response.body?.string()?.let { json ->
                stringData = json
            }
        }
        val contentType = response.body?.contentType()
        val body = ResponseBody.create(contentType,stringData)
        return response.newBuilder().body(body).build()
    }
}