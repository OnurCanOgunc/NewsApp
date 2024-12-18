package com.decode.composenews.util

import java.text.SimpleDateFormat
import java.util.Locale


fun String.formatDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.ENGLISH)
        val date = inputFormat.parse(this)

        val outputFormat = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.ENGLISH)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}