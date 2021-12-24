package com.test.moviebox.utils

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.test.moviebox.BaseApplication
import com.test.moviebox.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BaseWorker(context: Context,workerParams: WorkerParameters) : CoroutineWorker(context,workerParams) {
    @Inject
    lateinit var repository: MovieRepository
    override suspend fun doWork(): Result {
        BaseApplication().initDaggerComponent().inject(this)
        return try {
            withContext(Dispatchers.IO){
//                val data = repository.getMovieList(1)
                Log.i("called_is","datas abc")
                Result.success()
            }
        } catch (e : Exception){
//            Log.i("data_error",Gson().toJson(e.localizedMessage))
            Result.failure()
        }
    }


}