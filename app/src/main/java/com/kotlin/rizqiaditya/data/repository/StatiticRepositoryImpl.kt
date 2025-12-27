package com.kotlin.rizqiaditya.data.repository

import com.kotlin.rizqiaditya.data.mapper.toDomain
import com.kotlin.rizqiaditya.data.remote.api.StatisticApi
import com.kotlin.rizqiaditya.domain.model.Statistic
import com.kotlin.rizqiaditya.domain.repository.StatiticRepository
import javax.inject.Inject

class StatiticRepositoryImpl @Inject constructor(
    private val statisticApi: StatisticApi
) : StatiticRepository {
    override suspend fun getTodayStatistic(): Statistic? {
        val response = statisticApi.getTodayStatistic()
        return response.body()?.data?.toDomain()
    }
}
