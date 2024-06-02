package com.fsof.ref_client.controller.service

import com.fsof.ref_client.utils.APIs
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.fsof.ref_client.model.entity.IngredientInfo
import com.fsof.ref_client.model.entity.Ingredients

interface NutrientService {
    @POST(APIs.NUTRIENTS)
    /*suspend*/ fun createNutrients(@Body item: IngredientInfo): Call<Ingredients>
}