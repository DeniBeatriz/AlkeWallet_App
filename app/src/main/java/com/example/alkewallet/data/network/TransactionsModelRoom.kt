package com.example.alkewallet.data.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index


@Entity(
    tableName = "transactions",
    indices = [Index(value = ["remoteId"], unique = true)]
)
data class TransactionsModelRoom(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val remoteId: String? = null,
    val sender: String,
    val receiver: String,
    val amount: Double,
    val date: String,
    val description: String,
    val status: String = "COMPLETED",
    val type: String = "EXPENSE"

)