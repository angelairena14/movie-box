package com.test.moviebox.view.research

import kotlin.math.roundToInt

class CircleHandler(private val circle : Shape.Circle) : CalculateHandler() {
    override fun calculate(): Int {
        return (Math.PI * Math.pow(circle.rad.toDouble(),2.0).toInt()).roundToInt()
    }
}