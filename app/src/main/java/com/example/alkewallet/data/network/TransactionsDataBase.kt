package com.example.alkewallet.data.network

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.alkewallet.data.api.TransactionsDao

@Database(entities = [TransactionsModelRoom::class], version = 1)
abstract class TransactionsDataBase: RoomDatabase() {
    abstract fun transactionsDao(): TransactionsDao

    companion object {
        @Volatile
        private var INSTANCE: TransactionsDataBase? = null

        fun getDatabase(context: Context): TransactionsDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TransactionsDataBase::class.java,
                    "transactions_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}