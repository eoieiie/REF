package com.fsof.project.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//import java.io.Serializable

@Entity(tableName = "FoodImage")
data class ImageUrl(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("image_url") val image_url: String,
)//: Serializable