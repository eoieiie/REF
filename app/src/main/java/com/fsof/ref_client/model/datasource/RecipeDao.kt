package com.fsof.ref_client.model.datasource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import com.fsof.ref_client.model.entity.Recipes

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