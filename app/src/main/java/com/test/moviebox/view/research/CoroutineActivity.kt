package com.test.moviebox.view.research

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.test.moviebox.R
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.system.measureTimeMillis

class CoroutineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        runBlocking { // parent coroutine - THREAD MAIN
//            launch
//            mana yg cepat beres dia selesai duluan
//            val timeMilis = measureTimeMillis {
//                val job = launch(IO) { doTaskA() } // akan jalan di thread sesuai parent
//                val job2 = launch(IO) {  doTaskB() } // thread sesuai parent
//                job.join()
//                job2.join()
//            }
//            Log.i("data_is","operation $timeMilis")

//            withContext
//            walaupun dijalankan bersamaan, tetap urutannya task B akan di eksekusi setelah A
//            val timeMilis = measureTimeMillis {
//                val resultA = withContext(IO){doTaskAReturnString()}
//                val resultB = withContext(IO){doTaskBReturnString()}
//                Log.i("state_is","$resultA + $resultB")
//            }
//            Log.i("data_is","operation $timeMilis")

//            ASYNC AWAIT
//            mana yg cepat beres dia selesai duluan, tapi kalau membutuhkan result
//            coroutine akan stop ketika 2 2 fungsinya sudah berhasil didapatkan
//            meskipun ada yg beres duluan, hasilnya tetap bisa didapatkan kalau 2 2 uda beres
//            hanya JIKA fungsi tersebut require 2 2 nya. Kalau salah 1 aja, ya yg beres duluan maka
//            dia bisa lgsg didapat resultnya
//            val resultA = async(IO) { doTaskAReturnString() }
//            val resultb = async(IO) { doTaskBReturnString() }
//            Log.i("state_is","hasil ${resultA.await()} ${resultb.await()}")
//            Log.i("state_is","menjalankan fungsi setelah coroutine")

//            val job1 = async(IO) {
//                doTaskA()
//                doTaskB()
//            }
//            job1.join()
//            Log.i("state_is","menjalankan fungsi setelah coroutine")
        }
        Log.i("state_is","menjalankan fungsi setelah run blocking")
    }

    suspend fun doTaskA(){
        Log.i("state_is","Task A Started ${Thread.currentThread().name}")
        delay(5000L);
        Log.i("state_is","Task A Finished")
    }

    suspend fun doTaskB(){
        Log.i("state_is","Task B Started ${Thread.currentThread().name}")
        delay(1000L);
        Log.i("state_is","Task B Finished")
    }

    suspend fun doTaskAReturnString() : String{
        Log.i("state_is","Task A Started ${Thread.currentThread().name}")
        delay(7000L)
        Log.i("state_is","Task A Finished")
        return "fungsi A"
    }

    suspend fun doTaskBReturnString() : String{
        Log.i("state_is","Task B Started ${Thread.currentThread().name}")
        delay(100L)
        Log.i("state_is","Task B Finished")
        return "fungsi B"
    }
}