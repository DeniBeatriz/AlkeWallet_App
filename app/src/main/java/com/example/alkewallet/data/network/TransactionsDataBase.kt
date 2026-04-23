package com.example.alkewallet.data.network

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.alkewallet.data.api.TransactionsDao
import com.example.alkewallet.data.api.UserDao

@Database(
    entities = [TransactionsModelRoom::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TransactionsDataBase : RoomDatabase() {

    abstract fun transactionsDao(): TransactionsDao
    abstract fun userDao(): UserDao

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