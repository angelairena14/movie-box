package com.test.moviebox.view.research

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.test.moviebox.R

class TestingAActivity : AppCompatActivity() {
    lateinit var calculateHandler: CalculateHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing_aactivity)
//        initializeResult()
//        setPersonData(Person("angela",10),::printPersonData)
        liveData1.observe(this, Observer {
            Log.i("data_dari",it)
        })
//        liveData1.removeObservers(this)
        val type = "rectangle"
        calculateHandler = when(type){
            "circle" -> CircleHandler(Shape.Circle(5))
            "square" -> SquareHandler(Shape.Square(4))
            else -> RectangleHandler(Shape.Rectangle(20,3))
        }
        Log.i("hasil_calculate",calculateHandler.calculate().toString())
    }

    private fun initializeResult() {
        val type = "success"
        var resultClass : Result?= null
        resultClass = when(type){
            "loading" -> Result.Loading(true)
            "success" -> Result.Success(200,"success!")
            else -> Result.Error(400,"error!!")
        }
        resultClass.mapError()
    }

    fun Result.mapError(){
        when(this){
            is Result.Error -> Log.i("sealed_1","${this.code} message ${this.message}")
            is Result.Success -> Log.i("sealed_2","message ${this.message}")
            is Result.Loading -> Log.i("sealed_3", "loading ..${this.isLoading} ")
        }
    }

    fun setPersonData(person : Person, printPerson : (person : Person) -> Unit){
        printPerson(person)
    }

    fun printPersonData(person : Person){
        Log.i("person_Is","person :${person.name} age :${person.age}")
    }
}

data class Person(val name : String, val age : Int)