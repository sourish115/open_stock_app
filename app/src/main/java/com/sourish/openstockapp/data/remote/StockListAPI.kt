package com.sourish.openstockapp.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockListAPI {

    @GET("query?function=LISTING_STATUS")
    suspend fun getStockList(
        @Query("apiKey") apiKey : String
    ): ResponseBody

    companion object {
        const val API_KEY = "G3GQMBFUDRL0U7WB"
        const val BASE_URL = "https://alphavantage.co"
    }
}