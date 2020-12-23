package com.example.ugerregistration.model

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

class ApiService {
    private val BASE_URL = "http://192.168.98.254:8080/"


    val apiinstance: Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(Api::class.java)
    }

    fun createUser(params: RequestBody): Call<ApiResponse> {
        return apiinstance.createUser(params)
    }

    fun login(params: RequestBody): Call<ApiResponse> {
        return apiinstance.login(params)
    }

    fun userUpdate(params: RequestBody): Call<ApiResponse> {
        return apiinstance.userUpdate(params)
    }


}