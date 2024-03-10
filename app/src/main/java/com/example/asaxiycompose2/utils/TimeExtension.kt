package com.example.asaxiycompose2.utils

fun getTime(time: Int): String {
    val hour = time / 3600
    val minute = (time % 3600) / 60
    val second = time % 60

    val hourText = if (hour > 0) {
        if (hour < 10) "0$hour:"
        else "$hour:"
    } else ""

    val minuteText = if (minute < 10) "0$minute:"
    else "$minute:"

    val secondText = if (second < 10) "0$second"
    else "$second"

    return "$hourText$minuteText$secondText"
}