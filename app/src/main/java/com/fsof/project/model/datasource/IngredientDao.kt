package com.fsof.project.model.datasource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import com.fsof.project.model.entity.Ingredients

@Dao
interface IngredientDao {
    @Query("SELECT * FROM Ingredients")
    /*suspend*/ fun selectAll(): List<Ingredients>

    @Insert
    /*suspend*/ fun insertData(ingredient: Ingredients)

    @Update
    /*suspend*/ fun updateData(ingredients: Ingredients)

    @Delete
    /*suspend*/ fun deleteData(ingredients: Ingredients)
}