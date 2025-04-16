package com.example.myassssmentapplication.repository

import com.example.myassssmentapplication.api.ApiService
import com.example.myassssmentapplication.model.LoginResponse

class ArtworkRepository(private val apiService: ApiService) {
    suspend fun login(username: String, password: String): LoginResponse? {
        return apiService.login(mapOf("username" to username, "password" to password)).body()
    }

    suspend fun getArtworks(keypass: String): List<Map<String, Any>> {
        return apiService.getDashboard(keypass).body()?.entities ?: emptyList()
    }
} 