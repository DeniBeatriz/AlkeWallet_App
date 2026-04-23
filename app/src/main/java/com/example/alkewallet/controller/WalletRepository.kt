package com.example.alkewallet.controller

import com.example.alkewallet.data.api.ApiService
import com.example.alkewallet.data.api.TransactionsDao
import com.example.alkewallet.data.network.TransactionsModelRoom
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

                val entities = remoteTransactions.map { model ->
                    TransactionsModelRoom(
                        remoteId = model.id,
                        sender = model.sender,
                        receiver = model.receiver,
                        amount = model.amount,
                        date = model.date,
                        description = model.description,
                        status = "COMPLETED",
                        type = if (model.sender == "Ingreso Externo") "INCOME" else "EXPENSE"
                    )
                }

                entities.forEach { transactionsDao.insert(it) }
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

    suspend fun sendAndSaveTransaction(transaction: TransactionsModel): Result<Boolean> {
        return withContext(Dispatchers.IO) {

            val localType = if (transaction.sender == "Ingreso Externo") {
                "INCOME"
            } else {
                "EXPENSE"
            }

            val entity = TransactionsModelRoom(
                sender = transaction.sender,
                receiver = transaction.receiver,
                amount = transaction.amount,
                date = transaction.date,
                description = transaction.description,
                status = "PENDING",
                type = localType
            )

            val generatedId = transactionsDao.insert(entity).toInt()

            try {
                val response = apiService.sendTransaction(transaction)

                if (response.isSuccessful) {
                    val remoteTransaction = response.body()

                    if (remoteTransaction != null && remoteTransaction.id != null) {
                        transactionsDao.updateStatusAndRemoteId(
                            id = generatedId,
                            status = "COMPLETED",
                            remoteId = remoteTransaction.id
                        )
                    } else {
                        transactionsDao.updateStatus(
                            id = generatedId,
                            status = "COMPLETED"
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