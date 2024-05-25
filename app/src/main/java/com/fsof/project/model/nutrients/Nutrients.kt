package com.fsof.project.model.nutrients

import com.google.gson.annotations.SerializedName

data class Nutrients(
    @SerializedName("calories") val calories: Int,
    @SerializedName("carbohydrates") val carbohydrates: Int,
    @SerializedName("protein") val protein: Int,
    @SerializedName("fat") val fat: Int
)