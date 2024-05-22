package com.fsof.project.controller.service

import com.fsof.project.utils.API
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.fsof.project.model.Nutrient.Ingredient
import com.fsof.project.model.recipes.Recipe

interface RecipeService {
    @POST(API.RECIPES)
    /*suspend*/ fun createRecipe(@Body items: List<Ingredient>): Call<Recipe>
}