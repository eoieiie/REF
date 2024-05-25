package com.fsof.project.model.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fsof.project.model.entity.Ingredients

@Dao
interface IngredientsDao {
    @Query("SELECT * FROM Ingredients")
    /*suspend*/ fun selectAll(): List<Ingredients>

    @Insert
    /*suspend*/ fun insertData(ingredient: Ingredients)

    @Update
    /*suspend*/ fun updateData(ingredients: Ingredients)

    @Delete
   /*suspend*/ fun deleteData(ingredients: Ingredients)
}