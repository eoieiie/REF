package com.fsof.project.model.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import com.fsof.project.model.entity.Recipes

@Dao
interface RecipeDao {
    @Query("SELECT * FROM Recipes")
    /*suspend*/ fun getAllIngredients(): List<Recipes>

    @Insert
    /*suspend*/ fun insertData(recipes: Recipes)

    @Update
    /*suspend*/ fun updateData(recipes: Recipes)

    @Delete
    /*suspend*/ fun deleteData(recipes: Recipes)
}