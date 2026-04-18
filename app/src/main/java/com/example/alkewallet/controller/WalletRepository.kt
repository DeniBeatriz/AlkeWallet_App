package com.example.alkewallet.controller

import com.example.alkewallet.data.api.ApiService
import com.example.alkewallet.data.api.TransactionsDao
import com.example.alkewallet.data.network.TransactionsModelRoom
import com.example.alkewallet.model.TransactionsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class WalletRepository(
    private val transactionsDao: TransactionsDao,
    private val apiService: ApiService
) {

    /**
     * Obtiene transacciones específicas desde la API y las guarda en la base de datos local.
     */
    suspend fun fetchAndSaveTransactions(id: Int) {
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getTransactions(id)
                val entities = response.map { model ->
                    TransactionsModelRoom(
                        sender = model.sender,
                        receiver = model.receiver,
                        amount = model.amount,
                        date = model.date,
                        description = model.description,
                        status = "COMPLETED"
                    )
                }
                entities.forEach { transactionsDao.insert(it) }
            } catch (e: Exception) {
                // Error de red o mapeo, se ignoran los datos nuevos
            }
        }
    }

    /**
     * Sincroniza todas las transacciones de la API y devuelve la lista local actualizada.
     */
    suspend fun syncAndGetTransactions(): List<TransactionsModelRoom> {
        return withContext(Dispatchers.IO) {
            try {
                val apiData = apiService.getTransactions()
                val entities = apiData.map {
                    TransactionsModelRoom(
                        sender = it.sender,
                        receiver = it.receiver,
                        amount = it.amount,
                        date = it.date,
                        description = it.description,
                        status = "COMPLETED"
                    )
                }
                entities.forEach { transactionsDao.insert(it) }
                transactionsDao.getAll()
            } catch (e: Exception) {
                transactionsDao.getAll()
            }
        }
    }

    /**
     * Envía una transacción a la API. Se guarda localmente como PENDING de inmediato.
     * Si la API tiene éxito, el estado cambia a COMPLETED.
     */
    suspend fun sendAndSaveTransaction(transaction: TransactionsModel): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            // 1. Crear entidad local con estado PENDING
            val entity = TransactionsModelRoom(
                sender = transaction.sender,
                receiver = transaction.receiver,
                amount = transaction.amount,
                date = transaction.date,
                description = transaction.description,
                status = "PENDING"
            )

            // 2. Insertar en Room y obtener el ID generado
            val generatedIdLong = transactionsDao.insert(entity)
            val generatedId = generatedIdLong.toInt()



            try {
                // 3. Intentar envío a la API (Solo una vez)
                val response: Response<TransactionsModel> = apiService.sendTransaction(transaction)

                if (response.isSuccessful) {
                    // 4. Éxito: Actualizar el estado en Room
                    val successEntity = entity.copy(id = generatedId, status = "COMPLETED")
                    transactionsDao.insert(successEntity)
                    Result.success(true)
                } else {
                    // Error de servidor: El registro queda como PENDING en Room
                    Result.failure(Exception("Error de servidor: ${response.code()}"))
                }
            } catch (e: Exception) {
                // Error de red: El registro queda como PENDING en Room
                Result.failure(e)
            }
        }
    }

    /**
     * Obtiene todos los registros locales de la base de datos.
     */
    suspend fun getAllLocalTransactions(): List<TransactionsModelRoom> {
        return withContext(Dispatchers.IO) {
            transactionsDao.getAll()
        }
    }
}