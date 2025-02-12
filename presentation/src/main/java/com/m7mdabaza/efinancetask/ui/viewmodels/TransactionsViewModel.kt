package com.m7mdabaza.efinancetask.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.m7mdabaza.domain.entities.transactionHistory.TransactionsHistory
import com.m7mdabaza.domain.usecases.DeleteTransactionUseCase
import com.m7mdabaza.domain.usecases.GetTransactionsHistoryUseCase
import com.m7mdabaza.efinancetask.base.BaseViewModel
import com.m7mdabaza.efinancetask.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getTransactionsHistoryUseCase: GetTransactionsHistoryUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
) : BaseViewModel() {

    private val _transactionsList = SingleLiveEvent<List<TransactionsHistory>>()
    val transactionsList: SingleLiveEvent<List<TransactionsHistory>> get() = _transactionsList

    private val _deleteTransaction = SingleLiveEvent<TransactionsHistory?>()
    val deleteTransaction: SingleLiveEvent<TransactionsHistory?> get() = _deleteTransaction

    fun getAllTransactions() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            try {
                _transactionsList.postValue(getTransactionsHistoryUseCase.getTransactionsHistory())
            } catch (e: Exception) {
                _transactionsList.postValue(emptyList())
            }
        }

    }


    fun deleteTransaction(transactionsHistory: TransactionsHistory) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    deleteTransactionUseCase.deleteTransaction(transactionsHistory)
                    _deleteTransaction.postValue(transactionsHistory)
                } catch (e: Exception) {
                    _deleteTransaction.postValue(null)
                }
            }
        }
    }

}