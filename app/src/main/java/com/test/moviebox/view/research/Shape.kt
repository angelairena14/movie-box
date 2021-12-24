package com.test.moviebox.view.research

sealed class Shape {
    data class Rectangle(val width : Int, val height : Int) : Shape()
    data class Circle(val rad : Int) : Shape()
    data class Square(val width : Int) : Shape()
}