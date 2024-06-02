package com.fsof.ref_client.model.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fsof.ref_client.model.entity.Ingredients

@Database(entities = [Ingredients::class], version = 1, exportSchema = false)
@TypeConverters(EntityConverter::class)
abstract class IngredientDatabase: RoomDatabase(){

    abstract fun ingredientsDao(): IngredientDao

    companion object{

        @Volatile
        private var INSTANCE: IngredientDatabase? = null

        fun getInstance(context: Context): IngredientDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IngredientDatabase::class.java,
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
