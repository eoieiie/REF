package com.fsof.project.controller.service

import com.fsof.project.utils.APIs
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.fsof.project.model.entity.IngredientInfo
import com.fsof.project.model.entity.Ingredients

interface NutrientService {
    @POST(APIs.NUTRIENTS)
    /*suspend*/ fun createNutrients(@Body item: IngredientInfo): Call<Ingredients>
}