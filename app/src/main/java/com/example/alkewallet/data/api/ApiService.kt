package com.example.alkewallet.data.api

import com.example.alkewallet.model.TransactionsModel
import com.example.alkewallet.model.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("transactions")
    suspend fun getTransactions(): Response<List<TransactionsModel>>

    //Obtener transacciones del servidor
    @GET("transactions/{id}")
    suspend fun getTransactions(@Path("id") id: String): Response<TransactionsModel>

    @GET("users")
    suspend fun getUsers(): Response<List<UserModel>>

    //Enviar una nueva transacción
    @POST("transactions")
    suspend fun sendTransaction(@Body transaction: TransactionsModel): Response<TransactionsModel>


}