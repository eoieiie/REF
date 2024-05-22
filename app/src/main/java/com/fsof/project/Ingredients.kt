package com.fsof.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")

data class Ingredients(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,//기본키설정

    @ColumnInfo(name = "a")
    val something : String?,

    @ColumnInfo(name = "b")
    val others : String?

)
