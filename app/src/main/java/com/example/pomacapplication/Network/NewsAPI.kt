package com.example.pomacapplication.Network

import com.example.pomacapplication.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("topstories/v2/world.json")
    suspend fun getPopularNews(
        @Query("api-key")
        apiKey: String = "Ru9kpfJx0rdhGHgVa8nT0nL75mJajVVK"
    ): Response<NewsResponse>

}