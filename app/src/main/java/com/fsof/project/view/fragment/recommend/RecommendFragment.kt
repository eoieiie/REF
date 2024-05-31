package com.fsof.project.view.fragment.recommend

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fsof.project.databinding.FragmentRecommendBinding
import com.fsof.project.view.activity.RecommendActivity
import android.widget.RelativeLayout
import android.widget.Toast
import com.fsof.project.controller.RecipeController
import com.fsof.project.controller.client.RecipeClient
import com.fsof.project.model.entity.Ingredients
import com.fsof.project.model.datasource.IngredientDatabase
import com.fsof.project.model.repository.RecipeRepository

class RecommendFragment : Fragment() {

    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipeController: RecipeController
    private lateinit var recipeRepository: RecipeRepository
    private lateinit var ingredientsDatabase: IngredientDatabase

    private lateinit var morningIngredients: List<String>
    private lateinit var morningDetailedInstructions: String
    private lateinit var lunchIngredients: List<String>
    private lateinit var lunchDetailedInstructions: String
    private lateinit var dinnerIngredients: List<String>
    private lateinit var dinnerDetailedInstructions: String

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // init
        ingredientsDatabase = IngredientDatabase.getInstance(requireContext())
        recipeRepository = RecipeRepository(RecipeClient.recipeService)
        recipeController = RecipeController(recipeRepository)

        createRecipes(ingredientsDatabase.ingredientsDao().selectAll())

        binding.morningText.setOnClickListener {
            navigateToDetail("아침 메뉴", morningIngredients, morningDetailedInstructions)
        }

        binding.noonText.setOnClickListener {
            navigateToDetail("점심 메뉴", lunchIngredients, lunchDetailedInstructions)
        }

        binding.nightText.setOnClickListener {
            navigateToDetail("저녁 메뉴", dinnerIngredients, dinnerDetailedInstructions)
        }
    }

    private fun navigateToDetail(name: String, ingredients: List<String>, detailedInstructions: String) {
        val intent = Intent(requireContext(), RecommendActivity::class.java).apply {
            putExtra("name", name)
            putExtra("ingredients", ingredients.toTypedArray())
            putExtra("detailedInstructions", detailedInstructions)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createRecipes(input: List<Ingredients>) {
        recipeController.createRecipe(input) { recipes, throwable ->
            handler.post {
                if (throwable != null) {
                    Log.d("API", "Error: ${throwable.message}")
                    activity?.let { Toast.makeText(it, "레시피 생성 실패 ㅠㅠ", Toast.LENGTH_SHORT).show() }
                } else if (recipes != null) {
                    Log.d("API", "Recipes: $recipes")

                    // ImageView Disabled
                    binding.morningImage.visibility = View.GONE
                    binding.noonImage.visibility = View.GONE
                    binding.nightImage.visibility = View.GONE

                    // Align To Center
                    val morningLayoutParams =
                        binding.morningText.layoutParams as RelativeLayout.LayoutParams
                    morningLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
                    binding.morningText.layoutParams = morningLayoutParams
                    val noonLayoutParams =
                        binding.noonText.layoutParams as RelativeLayout.LayoutParams
                    noonLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
                    binding.noonText.layoutParams = noonLayoutParams
                    val nightLayoutParams =
                        binding.nightText.layoutParams as RelativeLayout.LayoutParams
                    nightLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
                    binding.nightText.layoutParams = nightLayoutParams

                    Log.d("API", recipes.toString()) // binding.recipeText.text = recipes.toString() // binding.recipeText.setText(recipes.toString())

                    // Binding
                    binding.morningText.text = recipes.breakfast.name
                    binding.noonText.text = recipes.lunch.name
                    binding.nightText.text = recipes.dinner.name

                    morningIngredients = recipes.breakfast.ingredients
                    morningDetailedInstructions = recipes.breakfast.detailedInstructions
                    lunchIngredients = recipes.breakfast.ingredients
                    lunchDetailedInstructions = recipes.breakfast.detailedInstructions
                    dinnerIngredients = recipes.breakfast.ingredients
                    dinnerDetailedInstructions = recipes.breakfast.detailedInstructions
                } else {
                    Log.d("API", "Unknown error occurred")
                    activity?.let { Toast.makeText(it, "레시피 생성 실패 ㅠㅠ", Toast.LENGTH_SHORT).show() }
                }
            }
        }
    }
}