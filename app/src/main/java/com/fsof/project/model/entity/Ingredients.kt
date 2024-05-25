package com.fsof.project.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fsof.project.model.Nutrient.Nutrients
//import com.google.gson.annotations.SerializedName

@Entity(tableName = "Ingredients")
data class Ingredients(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("name") val name: String,
    @ColumnInfo("weight") val weight: String,
    @ColumnInfo("isFreezed") val isFreezed: Boolean,
    @ColumnInfo("up_date") val upDate: String,
    @ColumnInfo("expiration_date") val expirationDate: String,
    @ColumnInfo("nutrients") val nutrients: Nutrients
)//: Serializable