package com.example.myassssmentapplication.model

data class DashboardResponse(
    val entities: List<Map<String, Any>>,
    val entityTotal: Int
) 