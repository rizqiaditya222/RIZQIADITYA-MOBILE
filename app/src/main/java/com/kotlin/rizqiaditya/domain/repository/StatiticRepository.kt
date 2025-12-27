package com.kotlin.rizqiaditya.domain.repository

import com.kotlin.rizqiaditya.domain.model.Statistic

interface StatiticRepository {
    suspend fun getTodayStatistic(): Statistic?
}
