package com.m7mdabaza.data.datasource

import com.m7mdabaza.domain.entities.payment.PaymentResponse
import retrofit2.Response
import retrofit2.http.POST

interface PaymentServices {

    @POST("objects")
    suspend fun confirmPayment(): Response<PaymentResponse>

}