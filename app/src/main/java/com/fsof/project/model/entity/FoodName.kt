package com.fsof.project.model.entity

import com.google.gson.annotations.SerializedName

data class FoodName(
    @SerializedName("food") val food: String
)