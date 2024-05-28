package com.example.coinranking.di

import com.example.coinranking.api.APIService
import com.example.coinranking.api.ApiInterceptor
import com.example.coinranking.data.repositories.APIRepositories
import com.example.coinranking.presentation.allCoin.AllCoinsViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val baseUrl: String = "https://api.coinranking.com/v2/"

val appModule = module {
    single<OkHttpClient> {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(ApiInterceptor())
            .build()
    }
    single<Retrofit> {
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(APIService::class.java) }
    single { APIRepositories(get()) }
    viewModel { AllCoinsViewModel(get()) }
}