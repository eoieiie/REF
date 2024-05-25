package com.fsof.project.controller

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.fsof.project.model.input.Input
import com.fsof.project.model.nutrients.Ingredient
import com.fsof.project.controller.service.NutrientsService
import android.util.Log

class NutrientController(private val nutrientService: NutrientsService) {
    fun createNutrients(item: Input, callback: (Ingredient?, Throwable?) -> Unit) {
        val call = nutrientService.createNutrients(item)
        call.enqueue(object : Callback<Ingredient> {
            override fun onResponse(call: Call<Ingredient>, response: Response<Ingredient>) {
                Log.d("API", "request onResponse")
                if (response.isSuccessful) {
                    callback(response.body(), null)
                    Log.d("API", "response successful")
                } else {
                    callback(null, Throwable("Response not successful: ${response.message()}"))
                    Log.d("API", "response successful")
                }
            }
            override fun onFailure(call: Call<Ingredient>, t: Throwable) {
                callback(null, t)
                Log.d("API", "request onFailure: $t")
            }
        })
    }
}