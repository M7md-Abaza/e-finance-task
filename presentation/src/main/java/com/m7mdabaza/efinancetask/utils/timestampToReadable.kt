package com.m7mdabaza.efinancetask.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.timestampToReadable(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy - hh:mm a", Locale.getDefault())
    return sdf.format(Date(this))
}

