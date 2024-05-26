package com.fsof.project.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fsof.project.model.entity.Recipes

@Database(entities = [Recipes::class], version = 1, exportSchema = false)
@TypeConverters(ModelConverter::class)
abstract class RecipeDatabase: RoomDatabase(){

    abstract fun recipeDao(): RecipeDao

    companion object{

        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getInstance(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "app_database"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
