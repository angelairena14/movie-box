package com.test.moviebox.utils

import java.text.SimpleDateFormat

fun formatDateStyle1(date : String) : String {
    var input = SimpleDateFormat("yyyy-MM-dd")
    var dateFormat = SimpleDateFormat("dd MMMM yyyy")
    val output = input.parse(date)
    return dateFormat.format(output)
}