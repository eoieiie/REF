package com.fsof.ref_client.controller.client

import com.fsof.ref_client.controller.service.RecipeService
import retrofit2.Retrofit
import com.fsof.ref_client.utils.APIs
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import com.fsof.ref_client.utils.APIs.TIMEOUT

object RecipeClient {
    val recipeService: RecipeService by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(APIs.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(RecipeService::class.java)
    }
}