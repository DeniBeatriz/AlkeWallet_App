package com.example.alkewallet.data.api

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.alkewallet.data.network.TransactionsModelRoom

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionsModelRoom): Long

    @Update
    suspend fun update(transaction: TransactionsModelRoom)

    @Delete
    suspend fun delete(transaction: TransactionsModelRoom)

    @Query("SELECT * FROM transactions ORDER BY id DESC")
    suspend fun getAll(): List<TransactionsModelRoom>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getById(id: Int): TransactionsModelRoom?

    @Query("SELECT * FROM transactions WHERE remoteId = :remoteId")
    suspend fun getByRemoteId(remoteId: String): TransactionsModelRoom?

    @Query("SELECT * FROM transactions WHERE status = 'PENDING'")
    suspend fun getPendingTransactions(): List<TransactionsModelRoom>

    @Query("UPDATE transactions SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Int, status: String)

    @Query("UPDATE transactions SET status = :status, remoteId = :remoteId WHERE id = :id")
    suspend fun updateStatusAndRemoteId(id: Int, status: String, remoteId: String)
}