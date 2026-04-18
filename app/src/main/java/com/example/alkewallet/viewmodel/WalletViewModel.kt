package com.example.alkewallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkewallet.controller.WalletRepository
import com.example.alkewallet.data.network.TransactionsModelRoom
import com.example.alkewallet.model.TransactionsModel
import kotlinx.coroutines.launch

class WalletViewModel(private val repository: WalletRepository) : ViewModel() {

    // Función para obtener y guardar datos de la API
    fun syncTransactions(id: Int) {
        viewModelScope.launch {
            repository.fetchAndSaveTransactions(id)
        }
    }

    // Función para enviar una nueva transacción
    fun sendMoney(transaction: TransactionsModel, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = repository.sendAndSaveTransaction(transaction)
            onResult(result.isSuccess)
        }
    }
    // Agrega esta función dentro de tu WalletViewModel
    suspend fun getAllLocalTransactions(): List<TransactionsModelRoom> {
        // El ViewModel simplemente le pide los datos al repositorio
        return repository.getAllLocalTransactions()
    }
}