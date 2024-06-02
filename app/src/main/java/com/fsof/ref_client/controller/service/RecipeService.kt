package com.fsof.ref_client.controller.service

import com.fsof.ref_client.utils.APIs
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.fsof.ref_client.model.entity.Ingredients
import com.fsof.ref_client.model.entity.Recipes

interface RecipeService {
    @POST(APIs.RECIPES)
    /*suspend*/ fun createRecipes(@Body items: List<Ingredients>): Call<Recipes>
}