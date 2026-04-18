package com.example.alkewallet.data.api

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alkewallet.data.network.TransactionsModelRoom

//Interfaz para acceder a la base de datos de transacciones
@Dao
interface TransactionsDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(transaction: TransactionsModelRoom): Long

    @Delete
    suspend fun delete(usuario: TransactionsModelRoom)

    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<TransactionsModelRoom>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getById(id: Int): TransactionsModelRoom

    @Query("SELECT * FROM transactions WHERE status = 'PENDING'")
    suspend fun getPendingTransactions(): List<TransactionsModelRoom>

}