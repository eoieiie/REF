package com.fsof.project.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//import java.io.Serializable

@Entity(tableName = "Ingredients")
data class Ingredients(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("name") val name: String,
    @ColumnInfo("weight") val weight: String,
    @ColumnInfo("isFreezed") val isFreezed: Boolean,
    @ColumnInfo("up_date") val up_date: String,
    @ColumnInfo("expiration_date") val expiration_date: String,
    @ColumnInfo("nutrients") val nutrients: Nutrient
)//: Serializable