package com.test.moviebox.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.moviebox.model.ErrorResponseModel
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject

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
        } else {
            response.body?.apply {
                val json_string = this.string()
                val jsonObj = JSONObject(json_string)
                val model = Gson().fromJson<ErrorResponseModel>(
                    jsonObj.toString(),
                    object : TypeToken<ErrorResponseModel>(){}.type
                )
               stringData = Gson().toJson(model)
            }
        }
        val contentType = response.body?.contentType()
        val body = ResponseBody.create(contentType,stringData)
        return response.newBuilder().body(body).build()
    }
}