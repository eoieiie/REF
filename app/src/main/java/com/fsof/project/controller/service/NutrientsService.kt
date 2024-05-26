package com.fsof.project.controller.service

import com.fsof.project.utils.APIs
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.fsof.project.model.input.Input
import com.fsof.project.model.entity.Ingredients

interface NutrientsService {
    @POST(APIs.NUTRIENTS)
    /*suspend*/ fun createNutrients(@Body item: Input): Call<Ingredients>
}