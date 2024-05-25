package com.fsof.project.model.recipes

import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("breakfast") val breakfast: Meal,
    @SerializedName("lunch") val lunch: Meal,
    @SerializedName("dinner") val dinner: Meal
)