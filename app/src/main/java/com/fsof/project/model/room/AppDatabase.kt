package com.fsof.project.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//import com.fsof.project.model.entity.SampleEntity

//@Database(entities = [SampleEntity::class], version = 1, exportSchema = false)
//abstract class AppDatabase: RoomDatabase(){
//
//    abstract fun appDao(): AppDao
//
//    companion object{
//
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "app_database"
//                )
//                    .allowMainThreadQueries()
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}
