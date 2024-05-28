package com.fsof.project.model.repository

import retrofit2.Call
import com.fsof.project.model.entity.IngredientInfo
import com.fsof.project.model.entity.Ingredients
import com.fsof.project.controller.service.NutrientService

class NutrientRepository(private val nutrientService: NutrientService) {
    fun createNutrients(item: IngredientInfo): Call<Ingredients> {
        return nutrientService.createNutrients(item)
    }
}