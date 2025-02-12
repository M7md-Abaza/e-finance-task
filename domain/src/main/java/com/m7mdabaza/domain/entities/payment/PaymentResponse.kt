package com.m7mdabaza.domain.entities.payment

import com.google.gson.annotations.SerializedName


data class PaymentResponse(
    val code: Int,
    val message: String,
    var id: String? = null,
    var name: String? = null,
    var data: Data? = Data()
)

data class Data(
    val color: String? = null,
    val capacity: String? = null,
    @SerializedName("capacity GB") val capacityGB: Int? = null,
    val price: Double? = null,
    val generation: String? = null,
    val year: Int? = null,
    @SerializedName("CPU model") val cpuModel: String? = null,
    @SerializedName("Hard disk size") val hardDiskSize: String? = null,
    @SerializedName("Strap Colour") val strapColour: String? = null,
    @SerializedName("Case Size") val caseSize: String? = null,
    @SerializedName("Color") val beatsColor: String? = null,
    val description: String? = null,
    @SerializedName("Screen size") val screenSize: Double? = null,
    @SerializedName("Generation") val iPadGeneration: String? = null,
    @SerializedName("Price") val iPadPrice: Double? = null,
    @SerializedName("Capacity") val iPadCapacity: String? = null

)