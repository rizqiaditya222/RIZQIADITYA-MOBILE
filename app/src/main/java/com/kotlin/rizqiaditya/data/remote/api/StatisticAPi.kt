package com.kotlin.rizqiaditya.data.remote.api

import com.kotlin.rizqiaditya.data.remote.dto.statistic.StatisticDto
import com.kotlin.rizqiaditya.data.remote.response.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface StatisticApi {
    @GET("statistics/today")
    suspend fun getTodayStatistic(): Response<BaseResponse<StatisticDto>>
}