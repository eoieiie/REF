package com.fsof.ref_client.controller.service

import com.fsof.ref_client.util.APIs
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.fsof.ref_client.model.entity.FoodName
import com.fsof.ref_client.model.entity.ImageUrl

interface FoodService {
    @POST(APIs.FOODS)
    /*suspend*/ fun createImage(@Body item: FoodName): Call<ImageUrl>
}