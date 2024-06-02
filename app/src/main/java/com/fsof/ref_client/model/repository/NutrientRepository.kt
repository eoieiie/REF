package com.fsof.ref_client.model.repository

import retrofit2.Call
import com.fsof.ref_client.model.entity.Ingredients
import com.fsof.ref_client.model.entity.IngredientInfo
import com.fsof.ref_client.controller.service.NutrientService

class NutrientRepository(private val nutrientService: NutrientService) {
    fun createNutrients(item: IngredientInfo): Call<Ingredients> {
        return nutrientService.createNutrients(item)
    }
}