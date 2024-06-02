package com.fsof.ref_client.model.repository

import retrofit2.Call
import com.fsof.ref_client.model.entity.Recipes
import com.fsof.ref_client.model.entity.Ingredients
import com.fsof.ref_client.controller.service.RecipeService

class RecipeRepository(private val recipeService: RecipeService) {
    fun createRecipes(items: List<Ingredients>): Call<Recipes> {
        return recipeService.createRecipes(items)
    }
}