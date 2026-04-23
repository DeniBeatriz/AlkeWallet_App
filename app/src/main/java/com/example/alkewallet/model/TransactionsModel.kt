package com.example.alkewallet.model

import androidx.room.PrimaryKey

data class TransactionsModel(
    val id: String? = null,
    val sender: String,
    val receiver: String,
    val amount: Double,
    val date: String,
    val description: String
)