package com.fsof.project.controller.client

import com.fsof.project.controller.service.NutrientsService
import com.fsof.project.utils.API
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NutrientClient {
    val nutrientService: NutrientsService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(API.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(NutrientsService::class.java)
    }
}
