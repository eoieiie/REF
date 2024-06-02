package com.fsof.ref_client.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fsof.ref_client.databinding.ActivityRecommendBinding

import com.fsof.ref_client.R
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.fsof.ref_client.model.entity.FoodName
import com.fsof.ref_client.controller.FoodController
import com.fsof.ref_client.controller.client.FoodClient
import com.fsof.ref_client.model.repository.FoodRepository

class RecommendActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRecommendBinding.inflate(layoutInflater, null, false) }

    private lateinit var foodController: FoodController
    private lateinit var foodRepository: FoodRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        foodRepository = FoodRepository(FoodClient.foodService)
        foodController = FoodController(foodRepository)

        createImage(intent.getStringExtra("name").toString())

        Glide.with(this).load(R.raw.loading).override(300, 300).into(binding.food)

        binding.recipeName.text = intent.getStringExtra("name").toString()
        binding.ingredients.text = intent.getStringArrayExtra("ingredients")?.joinToString("\n")
        binding.detailedInstructions.text = intent.getStringExtra("detailedInstructions")

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun createImage(foodName: String) {
        foodController.createImage(FoodName(foodName)) { response, throwable ->
            runOnUiThread {
                if (throwable != null || response == null) {
                    Toast.makeText(this, "이미지 생성 실패 ㅠㅠ", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("API", "URL: ${response.image_url}")
                    loadImage(response.image_url)
                }
            }
        }
    }

    private fun loadImage(url: String) {
        Glide.with(this)
            .load(url)
            .circleCrop()
            .into(binding.food)
    }
}