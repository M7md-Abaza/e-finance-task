package com.m7mdabaza.domain.usecases

import com.m7mdabaza.domain.entities.transactionHistory.TransactionsHistory
import com.m7mdabaza.domain.entities.transactionHistory.TransactionsHistoryDao
import javax.inject.Inject

class InsertTransactionUseCase @Inject constructor(private val dao: TransactionsHistoryDao) {
    suspend fun insertTransaction(transactionsHistory: TransactionsHistory) {
        dao.insertTransaction(transactionsHistory)
    }
}