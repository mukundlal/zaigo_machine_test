package com.example.test.network

import com.example.test.models.LoginResponse
import com.example.test.models.NewsResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("auth/login")
    fun userLogin(@Field("email") email:String,@Field("password") password:String):Call<LoginResponse>

    @GET("sweepstake")
    fun getSweepstake():Call<JSONObject>

    @GET("in.json")
    fun getNews():Call<NewsResponse>
}