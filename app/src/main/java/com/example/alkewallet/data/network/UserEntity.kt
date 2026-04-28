package com.example.alkewallet.data.network

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Int = 1,
    val name: String,
    val email: String,
    val imageUrl: String = ""
)