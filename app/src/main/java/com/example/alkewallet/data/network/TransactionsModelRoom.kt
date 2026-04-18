package com.example.alkewallet.data.network

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionsModelRoom(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sender: String,
    val receiver: String,
    val amount: Double,
    val date: String,
    val description: String,
    val status: String = "COMPLETED"

)