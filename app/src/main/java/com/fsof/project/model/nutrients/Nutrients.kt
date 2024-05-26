package com.fsof.project.model.nutrients

import com.google.gson.annotations.SerializedName

data class Nutrients(
    @SerializedName("calories") val calories: Int,
    @SerializedName("carbohydrates") val carbohydrates: Double,
    @SerializedName("protein") val protein: Double,
    @SerializedName("fat") val fat: Double
)