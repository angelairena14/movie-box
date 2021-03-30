package com.test.moviebox

import android.app.Application
import android.content.Context
import com.test.moviebox.di.APIComponent
import com.test.moviebox.di.ApiModule
import com.test.moviebox.di.DaggerAPIComponent

class BaseApplication : Application(){
    companion object {
        var ctx: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
    }

    fun getMyComponent(): APIComponent {
        return initDaggerComponent()
    }

    fun initDaggerComponent() : APIComponent {
        return DaggerAPIComponent
            .builder()
            .apiModule(ApiModule(BuildConfig.ENDPOINT_URL))
            .build()
    }
}