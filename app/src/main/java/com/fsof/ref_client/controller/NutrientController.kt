package com.fsof.ref_client.controller

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.fsof.ref_client.model.entity.Ingredients
import com.fsof.ref_client.model.entity.IngredientInfo
import com.fsof.ref_client.model.repository.NutrientRepository

class NutrientController(private val nutrientRepository: NutrientRepository) {
    fun createNutrients(item: IngredientInfo, callback: (Ingredients?, Throwable?) -> Unit) {
        val call = nutrientRepository.createNutrients(item)
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