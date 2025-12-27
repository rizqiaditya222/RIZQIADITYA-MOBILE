package com.kotlin.rizqiaditya.data.remote.dto.statistic

data class StatisticDto(
    val date: String,
    val todayVisit: Int,
    val todayComment: Int,
    val todayMessage: Int
)
