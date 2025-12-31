package com.kotlin.rizqiaditya.presentation.util


import com.kotlin.rizqiaditya.domain.model.Message
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Message.groupDate(): String {
    val instant = Instant.parse(createdAt)
    val localDate = instant.atZone(ZoneId.of("Asia/Jakarta")).toLocalDate()

    val formatter = DateTimeFormatter.ofPattern(
        "dd MMMM yyyy",
        Locale("id", "ID")
    )

    return localDate.format(formatter)
}

fun Message.groupTime(): String {
    val instant = Instant.parse(createdAt)
    val localTime = instant.atZone(ZoneId.of("Asia/Jakarta")).toLocalTime()

    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return localTime.format(formatter)
}
