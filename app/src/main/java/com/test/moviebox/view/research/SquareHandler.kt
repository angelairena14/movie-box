package com.test.moviebox.view.research

class SquareHandler(private val square: Shape.Square) : CalculateHandler() {

    override fun calculate(): Int {
        return Math.pow(square.width.toDouble(),2.0).toInt()
    }
}