package com.example.alkewallet

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.alkewallet.controller.WalletRepository
import com.example.alkewallet.data.network.TransactionsModelRoom
import com.example.alkewallet.viewmodel.WalletViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

//Este test simula el comportamiento del ViewModel y que elsaldo total sea la suma correcta

@OptIn(ExperimentalCoroutinesApi::class)
class WalletViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: WalletRepository
    private lateinit var viewModel: WalletViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = WalletViewModel(repository)

    }

    @After
    fun tearDown() {
       Dispatchers.resetMain()
    }

    @Test
    fun loadLocalTransactions_updatesTransactionsAndBalance() = runTest {
        val fakeTransactions = listOf(/* Datos de prueba */
            TransactionsModelRoom(
                sender = "Alice",
                receiver = "Bob",
                amount = 100.0,
                date = "2026-04-18",
                description = "Test transaction",
                status = "COMPLETED",
                type = "EXPENSE"

            ),
            TransactionsModelRoom(
                sender = "Charlie",
                receiver = "Dave",
                amount = 300.0,
                date = "2026-04-19",
                description = "Another test transaction",
                status = "COMPLETED",
                type = "EXPENSE"
            ))

        coEvery { repository.getAllLocalTransactions() } returns fakeTransactions

        viewModel.loadLocalTransactions()

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(2, viewModel.transactions.value?.size)
        assertEquals(400.0, viewModel.balance.value?:0.0, 0.01)
    }

}