package com.example.alkewallet.model

import androidx.room.PrimaryKey

data class TransactionsModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sender: String,
    val receiver: String,
    val amount: Double,
    val date: String,
    val description: String
)