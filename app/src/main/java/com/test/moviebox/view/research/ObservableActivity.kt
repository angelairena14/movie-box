package com.test.moviebox.view.research

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.test.moviebox.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_observable.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.*

class ObservableActivity : AppCompatActivity(), DialogFragment.Listener, LifecycleObserver {
    var disposable = Disposables.empty()
    var valueAfterBind = ""
    val value : MutableLiveData<String> = MutableLiveData()
    val liveData2 : MutableLiveData<String> = MutableLiveData()
    val stack = Stack<Int>()
    var index = (0..2).random()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observable)

        val observable = Observable.just("Apple","banana","grape","orange")
//        disposable = observable
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .map {
//                            "$it is fruit"
//                        }
//                        .subscribe({
//                           Log.i("hasil_emit",it)
//                        },{})

//        disposable = observable
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .flatMap {
//                Observable.just("$it is fruit")
//            }
//            .subscribe({
//                Log.i("hasil_emit",it)
//            },{})
        liveData1.postValue("INI LIVE DATA DARI UTILS")
        getValueLiveData(null).observe(this, Observer {
            it?.let {
                valueAfterBind = it
            }
        })
        liveData2.postValue("ini live data2")
        liveDataShape.postValue(Shape.Rectangle(10,20))
        btn_open_dialog.setOnClickListener {
//            val bottomSheet = DialogFragment()
//            bottomSheet.apply {
//                attachListener(this@ObservableActivity)
//            }.also { it ->
//                it.show(supportFragmentManager,"DialogFragment")
//            }
            randomize()
        }
        btn_get_value.setOnClickListener {
            Toast.makeText(this,valueAfterBind,Toast.LENGTH_SHORT).show()
        }
    }

    private fun randomize() {
        if (stack.isEmpty()){
            stack.push(index)
        } else {

        }
        Log.i("result_is"," res $index")
    }

    fun getVegetableObservable() : Observable<String> {
        return Observable.just("Carrot","cabbage","spinach","cucumber")
    }

    fun getValueLiveData(item : String?) : LiveData<String>{
//        LIVE DATA JGN DI INITIALIZE DI DALAM FUNCTION, KARNA JATOHNYA BUAT LIVEDATA BARU
        value.postValue(item)
        return value
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    override fun onClick(item: String) {
        getValueLiveData(item)
    }
}