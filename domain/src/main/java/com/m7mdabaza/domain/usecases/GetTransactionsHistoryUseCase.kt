package com.m7mdabaza.domain.usecases

import com.m7mdabaza.domain.entities.transactionHistory.TransactionsHistoryDao
import javax.inject.Inject

class GetTransactionsHistoryUseCase @Inject constructor(private val dao: TransactionsHistoryDao) {
    suspend fun getTransactionsHistory() = dao.getAllTransactions()
}