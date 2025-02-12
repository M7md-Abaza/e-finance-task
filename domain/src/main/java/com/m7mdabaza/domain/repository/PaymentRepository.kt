package com.m7mdabaza.domain.repository

import com.m7mdabaza.domain.entities.payment.PaymentResponse
import retrofit2.Response

interface PaymentRepository {
    suspend fun confirmPayment() : Response<PaymentResponse>
}