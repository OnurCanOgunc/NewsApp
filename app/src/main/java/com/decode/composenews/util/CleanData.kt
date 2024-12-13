package com.decode.composenews.util

fun String.cleanDate(): String {
    return this.substringBeforeLast(" +")
}