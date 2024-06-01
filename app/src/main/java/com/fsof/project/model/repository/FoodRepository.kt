package com.fsof.project.model.repository

import retrofit2.Call
import com.fsof.project.controller.service.FoodService
import com.fsof.project.model.entity.FoodName
import com.fsof.project.model.entity.ImageUrl

class FoodRepository(private val nutrientService: FoodService) {
    fun createImage(item: FoodName): Call<ImageUrl> {
        return nutrientService.createImage(item)
    }
}