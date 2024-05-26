package com.fsof.project.controller

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.fsof.project.model.input.Input
import com.fsof.project.model.entity.Ingredients
import com.fsof.project.controller.service.NutrientsService
import android.util.Log

class NutrientController(private val nutrientService: NutrientsService) {
    fun createNutrients(item: Input, callback: (Ingredients?, Throwable?) -> Unit) {
        val call = nutrientService.createNutrients(item)
        call.enqueue(object : Callback<Ingredients> {
            override fun onResponse(call: Call<Ingredients>, response: Response<Ingredients>) {
                Log.d("API", "request onResponse")
                if (response.isSuccessful) {
                    val ingredients = response.body()
                    if (ingredients != null) {
                        callback(ingredients, null)
                        Log.d("API", "response successful")
                    } else {
                        Log.e("API", "Response body is null")
                    }
                } else {
                    callback(null , Throwable("Response not successful: ${response.message()}"))
                    Log.e("API", "response not successful")
                }
            }
            override fun onFailure(call: Call<Ingredients>, t: Throwable) {
                callback(null , t)
                Log.e("API", "request onFailure: $t")
            }
        })
    }
}

//class NutrientController(private val nutrientService: NutrientsService) {
//    fun createNutrients(items: Input, callback: (Ingredients?, Throwable?) -> Unit) {
//        val call = recipeService.createNutrients(items)
//        call.enqueue(object : Callback<Ingredients> {
//            override fun onResponse(call: Call<Ingredients>, response: Response<Ingredients>) {
//                if (response.isSuccessful) {
//                    callback(response.body(), null)
//                } else {
//                    callback(null, Throwable("Response not successful: ${response.message()}"))
//                }
//            }
//            override fun onFailure(call: Call<Ingredients>, t: Throwable) {
//                callback(null, t)
//            }
//        })
//    }
//}