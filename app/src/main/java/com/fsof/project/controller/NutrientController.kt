package com.fsof.project.controller

import com.fsof.project.model.input.Input
import com.fsof.project.model.Nutrient.Ingredient
import com.fsof.project.controller.service.NutrientsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NutrientController(private val nutrientService: NutrientsService) {
    fun createNutrients(item: Input, callback: (Ingredient?, Throwable?) -> Unit) {
        val call = nutrientService.createNutrients(item)
        call.enqueue(object : Callback<Ingredient> {
            override fun onResponse(call: Call<Ingredient>, response: Response<Ingredient>) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, Throwable("Response not successful: ${response.message()}"))
                }
            }
            override fun onFailure(call: Call<Ingredient>, t: Throwable) {
                callback(null, t)
            }
        })
    }
}
