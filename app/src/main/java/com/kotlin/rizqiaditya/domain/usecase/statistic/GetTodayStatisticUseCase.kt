package com.kotlin.rizqiaditya.domain.usecase.statistic

import com.kotlin.rizqiaditya.domain.model.Statistic
import com.kotlin.rizqiaditya.domain.repository.StatiticRepository

class GetTodayStatisticUseCase constructor(
    private val repository: StatiticRepository
) {
    suspend operator fun invoke(): Statistic? = repository.getTodayStatistic()
}
