package com.m7mdabaza.data.roomsDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.m7mdabaza.domain.entities.transactionHistory.TransactionsHistory
import com.m7mdabaza.domain.entities.transactionHistory.TransactionsHistoryDao

@Database(entities = [TransactionsHistory::class], version = 1, exportSchema = false)
abstract class TransactionsHistoryDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionsHistoryDao

}