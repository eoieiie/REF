package com.fsof.project.model.repository

import retrofit2.Call
import com.fsof.project.model.entity.Recipes
import com.fsof.project.model.entity.Ingredients
import com.fsof.project.controller.service.RecipeService

class RecipeRepository(private val recipeService: RecipeService) {
    fun createRecipes(items: List<Ingredients>): Call<Recipes> {
        return recipeService.createRecipes(items)
    }
}