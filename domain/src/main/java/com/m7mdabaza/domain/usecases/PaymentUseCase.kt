package com.m7mdabaza.domain.usecases

import com.m7mdabaza.domain.entities.payment.PaymentResponse
import com.m7mdabaza.domain.repository.PaymentRepository
import retrofit2.Response
import javax.inject.Inject

class PaymentUseCase @Inject constructor(private val paymentRepository: PaymentRepository)  {
    suspend fun confirmPayment() : Response<PaymentResponse> {
        return paymentRepository.confirmPayment()
    }
}