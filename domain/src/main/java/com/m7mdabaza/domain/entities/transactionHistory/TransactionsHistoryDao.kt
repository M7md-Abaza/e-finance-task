package com.m7mdabaza.domain.entities.transactionHistory

import androidx.room.*

@Dao
interface TransactionsHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transactionsHistory: TransactionsHistory)

    @Query("SELECT * FROM transactions_history_table")
    suspend fun getAllTransactions(): List<TransactionsHistory>

    @Delete
    suspend fun deleteTransaction(transactionsHistory: TransactionsHistory)
}