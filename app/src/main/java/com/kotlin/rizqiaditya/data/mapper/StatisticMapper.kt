package com.kotlin.rizqiaditya.data.mapper

import com.kotlin.rizqiaditya.data.remote.dto.statistic.StatisticDto
import com.kotlin.rizqiaditya.domain.model.Statistic

fun StatisticDto.toDomain(): Statistic {
    return Statistic(
        date = date,
        todayVisit = todayVisit,
        todayComment = todayComment,
        todayMessage = todayMessage,
    )
}