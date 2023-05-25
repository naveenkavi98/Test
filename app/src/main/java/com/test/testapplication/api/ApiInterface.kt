package com.test.testapplication.api


import com.test.testapplication.model.UserResponse
import com.test.testapplication.model.UserResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("users")
    fun callUserListApi(): Call<UserResponse>

    @GET("users/{stringExtra}")
    fun callUserDetailsApi(@Path("stringExtra") stringExtra: String): Call<UserResponseItem>
}