package com.test.moviebox.view.research

class RectangleHandler(private val rectangle : Shape.Rectangle) : CalculateHandler() {
    override fun calculate(): Int {
        return rectangle.width * rectangle.height
    }
}