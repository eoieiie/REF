package com.fsof.project.model.input

import com.google.gson.annotations.SerializedName

data class Input(
    @SerializedName("name") val name: String,
    @SerializedName("weight") val weight: String,
    @SerializedName("isFreezed") val isFreezed: Boolean,
    @SerializedName("up_date") val up_date: String,
    @SerializedName("expiration_date") val expiration_date: String
)