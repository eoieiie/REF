package com.fsof.ref_client.model.entity

import com.google.gson.annotations.SerializedName

data class Nutrient(
    @SerializedName("calories") val calories: Int,
    @SerializedName("carbohydrates") val carbohydrates: Double,
    @SerializedName("protein") val protein: Double,
    @SerializedName("fat") val fat: Double
)