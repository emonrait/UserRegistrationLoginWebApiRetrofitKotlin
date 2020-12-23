package com.example.ugerregistration.model

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @POST("/addUser")
    fun createUser(
        @Body params: RequestBody?
    ): Call<ApiResponse>


    @POST("/login")
    fun login(
        @Body params: RequestBody
    ): Call<ApiResponse>


    @PUT("/update}")
    fun userUpdate(
        @Body params: RequestBody?
    ): Call<ApiResponse>


}