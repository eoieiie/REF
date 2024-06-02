package com.fsof.ref_client.controller

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.fsof.ref_client.model.entity.FoodName
import com.fsof.ref_client.model.entity.ImageUrl
import com.fsof.ref_client.model.repository.FoodRepository

class FoodController(private val foodRepository: FoodRepository) {
    fun createImage(item: FoodName, callback: (ImageUrl?, Throwable?) -> Unit) {
        val call = foodRepository.createImage(item)
        call.enqueue(object : Callback<ImageUrl> {
            override fun onResponse(call: Call<ImageUrl>, response: Response<ImageUrl>) {
                Log.d("API", "request onResponse")
                if (response.isSuccessful) {
                    val url = response.body()
                    if (url != null) {
                        callback(url, null)
                        Log.d("API", "response successful")
                    } else {
                        Log.e("API", "Response body is null")
                    }
                } else {
                    callback(null , Throwable("Response not successful: ${response.message()}"))
                    Log.e("API", "response not successful")
                }
            }
            override fun onFailure(call: Call<ImageUrl>, t: Throwable) {
                callback(null , t)
                Log.e("API", "request onFailure: $t")
            }
        })
    }
}