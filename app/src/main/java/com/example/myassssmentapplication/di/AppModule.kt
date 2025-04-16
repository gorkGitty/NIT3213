package com.example.myassssmentapplication.di

import com.example.myassssmentapplication.api.ApiService
import com.example.myassssmentapplication.repository.ArtworkRepository
import com.example.myassssmentapplication.ui.DashboardViewModel
import com.example.myassssmentapplication.ui.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://nit3213api.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    single { ArtworkRepository(get()) }

    viewModel { LoginViewModel(get()) }
    viewModel { DashboardViewModel(get()) }
} 