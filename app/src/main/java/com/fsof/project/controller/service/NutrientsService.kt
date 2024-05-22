package com.fsof.project.controller.service

import com.fsof.project.utils.API
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.fsof.project.model.Input.Input
import com.fsof.project.model.Nutrient.Ingredient

interface NutrientsService {
    @POST(API.NUTRIENTS)
    /*suspend*/ fun createNutrients(@Body items: Input): Call<Ingredient>
}