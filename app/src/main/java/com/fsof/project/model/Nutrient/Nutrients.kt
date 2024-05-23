package com.fsof.project.model.Nutrient

import com.google.gson.annotations.SerializedName

data class Nutrients(
    @SerializedName("calories") val calories: Int,
    @SerializedName("carbohydrates") val carbohydrates: Int,
    @SerializedName("protein") val protein: Int,
    @SerializedName("fat") val fat: Int
)