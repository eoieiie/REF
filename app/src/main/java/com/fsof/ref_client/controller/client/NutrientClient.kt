package com.fsof.ref_client.controller.client

import com.fsof.ref_client.controller.service.NutrientService
import retrofit2.Retrofit
import com.fsof.ref_client.util.APIs
import retrofit2.converter.gson.GsonConverterFactory

object NutrientClient {
    val nutrientService: NutrientService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(APIs.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(NutrientService::class.java)
    }
}