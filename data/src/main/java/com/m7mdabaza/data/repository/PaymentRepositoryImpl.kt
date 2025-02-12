package com.m7mdabaza.data.repository

import com.m7mdabaza.data.datasource.PaymentRemoteDataSource
import com.m7mdabaza.domain.entities.payment.PaymentRequest
import com.m7mdabaza.domain.entities.payment.PaymentResponse
import com.m7mdabaza.domain.repository.PaymentRepository
import retrofit2.Response
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val paymentRemoteDataSource : PaymentRemoteDataSource
) : PaymentRepository {
    override suspend fun confirmPayment(paymentRequest: PaymentRequest): Response<PaymentResponse> {
        return paymentRemoteDataSource.confirmPayment(paymentRequest)
    }
}