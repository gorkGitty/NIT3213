package com.example.myassssmentapplication

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("sydney/auth") // change this based on your class location
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}
