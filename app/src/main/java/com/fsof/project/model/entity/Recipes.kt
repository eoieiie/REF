package com.fsof.project.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fsof.project.model.recipes.Meal
//import com.google.gson.annotations.SerializedName

@Entity(tableName = "Recipes")
data class Recipes(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("breakfast") val breakfast: Meal,
    @ColumnInfo("lunch") val lunch: Meal,
    @ColumnInfo("dinner") val dinner: Meal
)//: Serializable