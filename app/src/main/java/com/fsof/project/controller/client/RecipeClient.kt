package com.fsof.project.controller.client

import com.fsof.project.controller.service.RecipeService
import com.fsof.project.utils.API
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RecipeClient {
    val recipeService: RecipeService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(API.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(RecipeService::class.java)
    }
}
