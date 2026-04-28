package com.example.alkewallet.model

data class SendTransactionRequest(
    val sender: String,
    val receiver: String,
    val amount: Double,
    val date: String,
    val description: String
)
