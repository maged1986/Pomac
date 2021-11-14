package com.example.pomacapplication.repository

import com.example.pomacapplication.Network.RetrofitInstance

class NewsRepository {
    //getting data from api
    suspend fun getPopularNews() =
        RetrofitInstance.api.getPopularNews()
}