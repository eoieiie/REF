package com.fsof.ref_client.controller

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.fsof.ref_client.model.entity.Recipes
import com.fsof.ref_client.model.entity.Ingredients
import com.fsof.ref_client.model.repository.RecipeRepository

class RecipeController(private val recipeRepository: RecipeRepository) {
    fun createRecipe(items: List<Ingredients>, callback: (Recipes?, Throwable?) -> Unit) {
        val call = recipeRepository.createRecipes(items)
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