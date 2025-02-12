package com.m7mdabaza.domain.entities.transactionHistory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions_history_table")
data class TransactionsHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val timestamp: Long
)