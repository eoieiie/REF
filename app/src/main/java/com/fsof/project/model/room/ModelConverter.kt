package com.fsof.project.model.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.fsof.project.model.nutrients.Nutrients
import com.fsof.project.model.recipes.Meal
//import com.fsof.project.model.entity.Ingredients
//import com.fsof.project.model.entity.Recipes

class ModelConverter {
    // Ingredients
    @TypeConverter
    fun fromNutrients(nutrients: Nutrients): String {
        return Gson().toJson(nutrients)
    }

    @TypeConverter
    fun toNutrients(nutrientsString: String): Nutrients {
        val nutrientsType = object : TypeToken<Nutrients>() {}.type
        return Gson().fromJson(nutrientsString, nutrientsType)
    }

//    @TypeConverter
//    fun ingredientsJsonToList(value: List<Ingredients>?): String? {
//        return Gson().toJson(value)
//    }
//    // fun listToJson(list: List<Ingredients>?) = Gson().toJson(list)
//
//    @TypeConverter
//    fun ingredientsJsonToList(value: String): List<Ingredients>? {
//        return Gson().fromJson(value,Array<Ingredients>::class.java)?.toList()
//    }
//    // fun jsonToList(value: String) = Gson().fromJson(value, Array<Ingredients>::class.java).toList()

    // Recipe
    @TypeConverter
    fun fromMeal(meal: Meal): String {
        return Gson().toJson(meal)
    }

    @TypeConverter
    fun toMeal(mealString: String): Meal {
        val mealType = object : TypeToken<Meal>() {}.type
        return Gson().fromJson(mealString, mealType)
    }

//    @TypeConverter
//    fun recipeJsonToJson(list: List<Recipes>?) = Gson().toJson(list)
////    fun listToJson(value: List<Recipes>?): String? {
////        return Gson().toJson(value)
////    }
//
//    @TypeConverter
//    fun recipeJsonToList(value: String) = Gson().fromJson(value, Array<Recipes>::class.java).toList()
////    fun jsonToList(value: String): List<Recipes>? {
////        return Gson().fromJson(value,Array<Recipes>::class.java)?.toList()
////    }
}