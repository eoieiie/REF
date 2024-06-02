package com.fsof.ref_client.model.repository

import retrofit2.Call
import com.fsof.ref_client.controller.service.FoodService
import com.fsof.ref_client.model.entity.FoodName
import com.fsof.ref_client.model.entity.ImageUrl

class FoodRepository(private val nutrientService: FoodService) {
    fun createImage(item: FoodName): Call<ImageUrl> {
        return nutrientService.createImage(item)
    }
}