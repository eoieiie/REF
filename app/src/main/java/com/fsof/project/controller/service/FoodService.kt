package com.fsof.project.controller.service

import com.fsof.project.utils.APIs
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.fsof.project.model.entity.FoodName
import com.fsof.project.model.entity.ImageUrl

interface FoodService {
    @POST(APIs.FOODS)
    /*suspend*/ fun createImage(@Body item: FoodName): Call<ImageUrl>
}