package com.test.moviebox.view.research

sealed class Result{
    data class Loading(val isLoading : Boolean) : Result()
    data class Success(val code : Int, val message : String) : Result()
    data class Error(val code : Int, val message : String) : Result()
}
