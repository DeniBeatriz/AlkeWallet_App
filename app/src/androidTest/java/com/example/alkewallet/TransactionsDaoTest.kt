package com.example.alkewallet

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.alkewallet.data.network.TransactionsDao
import com.example.alkewallet.data.network.TransactionsDataBase
import com.example.alkewallet.data.network.TransactionsModelRoom
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

//Esta prueba crea la base en la memoria, el DAO inserta la transacción y getAll recupera el dato guardado
@RunWith(AndroidJUnit4::class)
class TransactionsDaoTest {

    private lateinit var database: TransactionsDataBase
    private lateinit var dao: TransactionsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TransactionsDataBase::class.java
        ).allowMainThreadQueries().build()
        dao = database.transactionsDao()

    }

    @After
    fun tearDown() {
        database.close()

    }

    @Test
    fun insertTransaction_andGetAll_returnsSavedTransaction() = runBlocking {
        val transaction = TransactionsModelRoom(
            sender = "Alice",
            receiver = "Bob",
            amount = 100.0,
            date = "2026-04-18",
            description = "Test transaction",
            status = "COMPLETED",
            type = "EXPENSE"
        )
        dao.insert(transaction)

        val result = dao.getAll()

        Assert.assertEquals(1, result.size)
        Assert.assertNotNull(result[0])
        Assert.assertEquals("Alice", result[0].sender)
        Assert.assertEquals("Bob", result[0].receiver)
        Assert.assertEquals(100.0, result[0].amount, 0.01)
        Assert.assertEquals("Prueba Room", result[0].description)
        Assert.assertEquals("COMPLETED", result[0].status)


    }

}