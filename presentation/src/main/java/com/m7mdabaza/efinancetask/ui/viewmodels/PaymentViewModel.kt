package com.m7mdabaza.efinancetask.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.m7mdabaza.domain.entities.transactionHistory.TransactionsHistory
import com.m7mdabaza.domain.usecases.InsertTransactionUseCase
import com.m7mdabaza.domain.usecases.PaymentUseCase
import com.m7mdabaza.efinancetask.base.BaseViewModel
import com.m7mdabaza.efinancetask.utils.NetworkState
import com.m7mdabaza.efinancetask.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val paymentUseCase: PaymentUseCase
) : BaseViewModel() {

    private val _confirmPayment = SingleLiveEvent<NetworkState>()
    val confirmPayment: SingleLiveEvent<NetworkState> get() = _confirmPayment

    private val _insertTransaction = SingleLiveEvent<Boolean>()
    val insertTransaction: SingleLiveEvent<Boolean> get() = _insertTransaction

    fun confirmPayment() {
        _confirmPayment.value = NetworkState.Loading

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    runApi(_confirmPayment, paymentUseCase.confirmPayment())
                } catch (e: Exception) {
                    _confirmPayment.postValue(NetworkState.Error(401))
                }
            }

        }
    }


    fun insertTransaction(transactionsHistory: TransactionsHistory) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    insertTransactionUseCase.insertTransaction(transactionsHistory)
                    _insertTransaction.postValue(true)
                } catch (e: Exception) {
                    _insertTransaction.postValue(false)
                }
            }
        }

    }

}