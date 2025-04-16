package com.example.myassssmentapplication.api

import com.example.myassssmentapplication.model.DashboardResponse
import com.example.myassssmentapplication.model.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("sydney/auth")
    suspend fun login(
        @Body credentials: Map<String, String>
    ): Response<LoginResponse>

    @GET("dashboard/{keypass}")
    suspend fun getDashboard(
        @Path("keypass") keypass: String
    ): Response<DashboardResponse>
} 