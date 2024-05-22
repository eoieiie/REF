package com.fsof.project.model.Nutrient

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("name") val name: String,
    @SerializedName("weight") val weight: String,
    @SerializedName("isFreezed") val isFreezed: Boolean,
    @SerializedName("up_date") val upDate: String,
    @SerializedName("expiration_date") val expirationDate: String,
    @SerializedName("nutrients") val nutrients: Nutrients
)