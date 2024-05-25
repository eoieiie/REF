package com.fsof.project.controller

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.fsof.project.controller.service.RecipeService
import com.fsof.project.model.entity.Ingredients
import com.fsof.project.model.entity.Recipes
import android.util.Log

class RecipeController(private val recipeService: RecipeService) {
    fun createRecipe(items: List<Ingredients>, callback: (Recipes?, Throwable?) -> Unit) {
        val call = recipeService.createRecipe(items)
        call.enqueue(object : Callback<Recipes> {
            override fun onResponse(call: Call<Recipes>, response: Response<Recipes>) {
                Log.d("API", "request onResponse")
                if (response.isSuccessful) {
                    val recipes = response.body()
                    if (recipes != null) {
                        callback(recipes, null)
                        Log.d("API", "response successful")
                    } else {
                        Log.e("API", "Response body is null")
                    }
                } else {
                    callback(null , Throwable("Response not successful: ${response.message()}"))
                    Log.e("API", "response not successful")
                }
            }
            override fun onFailure(call: Call<Recipes>, t: Throwable) {
                callback(null , t)
                Log.e("API", "request onFailure: $t")
            }
        })
    }
}

//class RecipeController(private val recipeService: RecipeService) {
//    fun createRecipe(items: List<Ingredients>, callback: (Recipes?, Throwable?) -> Unit) {
//        val call = recipeService.createRecipe(items)
//        call.enqueue(object : Callback<Recipes> {
//            override fun onResponse(call: Call<Recipes>, response: Response<Recipes>) {
//                if (response.isSuccessful) {
//                    callback(response.body(), null)
//                } else {
//                    callback(null, Throwable("Response not successful: ${response.message()}"))
//                }
//            }
//            override fun onFailure(call: Call<Recipes>, t: Throwable) {
//                callback(null, t)
//            }
//        })
//    }
//}