package com.fsof.project.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fsof.project.model.entity.Ingredients

@Database(entities = [Ingredients::class], version = 1, exportSchema = false)
abstract class IngredientsDatabase: RoomDatabase(){

    abstract fun ingredientsDao(): IngredientsDao

    companion object{

        @Volatile
        private var INSTANCE: IngredientsDatabase? = null

        fun getInstance(context: Context): IngredientsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IngredientsDatabase::class.java,
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
