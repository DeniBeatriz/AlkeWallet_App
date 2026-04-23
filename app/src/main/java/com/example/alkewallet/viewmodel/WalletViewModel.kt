package com.example.alkewallet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkewallet.controller.WalletRepository
import com.example.alkewallet.data.network.TransactionsModelRoom
import com.example.alkewallet.model.TransactionsModel
import kotlinx.coroutines.launch

class WalletViewModel(private val repository: WalletRepository) : ViewModel() {

    private val _transactions = MutableLiveData<List<TransactionsModelRoom>>()
    val transactions: LiveData<List<TransactionsModelRoom>> get() = _transactions

    private val _balance = MutableLiveData<Double>()
    val balance: LiveData<Double> get() = _balance

    private val _operationStatus = MutableLiveData<Boolean>()
    val operationStatus: LiveData<Boolean> get() = _operationStatus

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun syncTransactions() {
        viewModelScope.launch {
            try {
                repository.fetchAndSaveTransactions()
                loadLocalTransactions()
            } catch (e: Exception) {
                _errorMessage.value = "No fue posible sincronizar las transacciones"
            }
        }
    }

    fun sendMoney(transaction: TransactionsModel) {
        viewModelScope.launch {
            try {
                val result = repository.sendAndSaveTransaction(transaction)
                _operationStatus.value = result.isSuccess

                if (result.isSuccess) {
                    loadLocalTransactions()
                } else {
                    _errorMessage.value =
                        result.exceptionOrNull()?.message ?: "No fue posible procesar la transacción"
                    loadLocalTransactions()
                }
            } catch (e: Exception) {
                _operationStatus.value = false
                _errorMessage.value = "Ocurrió un error al enviar la transacción"
            }
        }
    }

    fun loadLocalTransactions() {
        viewModelScope.launch {
            try {
                val history = repository.getAllLocalTransactions()
                _transactions.value = history

                val total = history
                    .filter { it.status == "COMPLETED" }
                    .sumOf { item ->
                        when (item.type) {
                            "INCOME" -> item.amount
                            "EXPENSE" -> -item.amount
                            else -> 0.0
                        }
                    }

                _balance.value = total
            } catch (e: Exception) {
                _errorMessage.value = "No fue posible cargar el historial"
            }
        }
    }
}