package com.fsof.project

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface IngredientsDao {

    @Query("SELECT * FROM ingredients")
    fun getAll(): List<Ingredients>

    @Insert
    fun insertData(ingredients: Ingredients)

    @Update
    fun updateData(ingredients: Ingredients)

    @Delete
    fun deleteData(ingredients: Ingredients)
}