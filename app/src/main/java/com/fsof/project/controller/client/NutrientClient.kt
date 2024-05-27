package com.fsof.project.controller.client

import com.fsof.project.controller.service.NutrientsService
import retrofit2.Retrofit
import com.fsof.project.utils.APIs
import retrofit2.converter.gson.GsonConverterFactory

object NutrientClient {
    val nutrientService: NutrientsService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(APIs.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(NutrientsService::class.java)
    }
}
