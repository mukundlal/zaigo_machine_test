package com.example.test.network

import com.example.test.common.Contants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient2{
     val apiInterface:ApiInterface by lazy {
        Retrofit.Builder().baseUrl("https://saurav.tech/NewsAPI/top-headlines/category/health/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

}
