package com.example.alkewallet.controller

import com.example.alkewallet.data.api.ApiService
import com.example.alkewallet.data.api.TransactionsDao
import com.example.alkewallet.data.network.TransactionsModelRoom
import com.example.alkewallet.model.SendTransactionRequest
import com.example.alkewallet.model.TransactionsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WalletRepository(
    private val transactionsDao: TransactionsDao,
    private val apiService: ApiService
) {

    suspend fun fetchAndSaveTransactions() {
        withContext(Dispatchers.IO) {
            val response = apiService.getTransactions()

            if (response.isSuccessful) {
                val remoteTransactions = response.body().orEmpty()

                remoteTransactions.forEach { model ->
                    val entity = TransactionsModelRoom(
                        remoteId = model.id,
                        sender = model.sender,
                        receiver = model.receiver,
                        amount = model.amount,
                        date = model.date,
                        description = model.description,
                        status = "COMPLETED",
                        type = "EXPENSE"
                    )
                    transactionsDao.insert(entity)

                }
            } else {
                throw Exception("Error HTTP ${response.code()}")
            }
        }
    }

    suspend fun syncAndGetTransactions(): List<TransactionsModelRoom> {
        return withContext(Dispatchers.IO) {
            try {
                fetchAndSaveTransactions()
                transactionsDao.getAll()
            } catch (e: Exception) {
                transactionsDao.getAll()
            }
        }
    }

    suspend fun sendAndSaveTransaction(transaction: TransactionsModel, type: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {

            val entity = TransactionsModelRoom(
                sender = transaction.sender,
                receiver = transaction.receiver,
                amount = transaction.amount,
                date = transaction.date,
                description = transaction.description,
                status = "PENDING",
                type = type
            )

            val generatedId = transactionsDao.insert(entity).toInt()

            try {
                val request = SendTransactionRequest(
                    sender = transaction.sender,
                    receiver = transaction.receiver,
                    amount = transaction.amount,
                    date = transaction.date,
                    description = transaction.description
                )

                val response = apiService.sendTransaction(request)

                if (response.isSuccessful) {
                    val remoteTransaction = response.body()

                    if (remoteTransaction != null && remoteTransaction.id != null) {
                        transactionsDao.updateStatusAndRemoteId(
                            generatedId,
                            "COMPLETED",
                            remoteTransaction.id
                        )
                    } else {
                        transactionsDao.updateStatus(
                            generatedId,
                            "COMPLETED"
                        )
                    }

                    Result.success(true)
                } else {
                    transactionsDao.updateStatus(generatedId, "FAILED")
                    Result.failure(Exception("Error de servidor: ${response.code()}"))
                }
            } catch (e: Exception) {
                transactionsDao.updateStatus(generatedId, "FAILED")
                Result.failure(e)
            }
        }
    }

    suspend fun getAllLocalTransactions(): List<TransactionsModelRoom> {
        return withContext(Dispatchers.IO) {
            transactionsDao.getAll()
        }
    }
}