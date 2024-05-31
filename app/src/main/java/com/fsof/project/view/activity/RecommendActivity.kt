package com.fsof.project.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fsof.project.databinding.ActivityRecommendBinding

class RecommendActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRecommendBinding.inflate(layoutInflater, null, false) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // setContentView(R.layout.activity_recommend)

        binding.recipeName.text = intent.getStringExtra("name")
        binding.ingredients.text = intent.getStringArrayExtra("ingredients")?.joinToString("\n")
        binding.detailedInstructions.text = intent.getStringExtra("detailedInstructions")

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}
