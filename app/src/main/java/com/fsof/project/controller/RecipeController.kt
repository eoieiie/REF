package com.fsof.project.controller

import com.fsof.project.model.nutrients.Ingredient
import com.fsof.project.model.recipes.Recipe
import com.fsof.project.controller.service.RecipeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeController(private val recipeService: RecipeService) {
    fun createRecipe(items: List<Ingredient>, callback: (Recipe?, Throwable?) -> Unit) {
        val call = recipeService.createRecipe(items)
        call.enqueue(object : Callback<Recipe> {
            override fun onResponse(call: Call<Recipe>, response: Response<Recipe>) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, Throwable("Response not successful: ${response.message()}"))
                }
            }
            override fun onFailure(call: Call<Recipe>, t: Throwable) {
                callback(null, t)
            }
        })
    }
}