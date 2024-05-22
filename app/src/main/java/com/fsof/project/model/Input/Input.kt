package com.fsof.project.model.Input

import com.google.gson.annotations.SerializedName

data class Input(
    @SerializedName("name") val name: String,
    @SerializedName("weight") val weight: String,
    @SerializedName("isFreezed") val isFreezed: Boolean,
    @SerializedName("up") val up: String,
    @SerializedName("expiration") val expiration: String,
)