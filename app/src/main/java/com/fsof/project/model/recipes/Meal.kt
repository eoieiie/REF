package com.fsof.project.model.recipes

import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("name") val name: String,
    @SerializedName("ingredients") val ingredients: List<String>,
    @SerializedName("detailed_instructions") val detailedInstructions: String
)