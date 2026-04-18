package com.example.alkewallet.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // La URL base debe terminar en / y no incluir el endpoint específico
    private const val BASE_URL = "https://69d9aebc26585bd92dd331f5.mockapi.io/api/v1/"

    val instance : ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}