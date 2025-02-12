package com.m7mdabaza.data.datasource

import com.m7mdabaza.domain.entities.payment.PaymentResponse
import retrofit2.Response
import javax.inject.Inject

class PaymentRemoteDataSource @Inject constructor(private val paymentServices: PaymentServices) {
    suspend fun confirmPayment() : Response<PaymentResponse> {
        return paymentServices.confirmPayment()
    }
}