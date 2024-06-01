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

import com.fsof.project.R
import android.widget.Toast
import com.bumptech.glide.Glide
import android.widget.RelativeLayout
import com.bumptech.glide.RequestManager
import com.fsof.project.controller.RecipeController
import com.fsof.project.controller.client.RecipeClient
import com.fsof.project.model.entity.Ingredients
import com.fsof.project.model.datasource.IngredientDatabase
import com.fsof.project.model.entity.Recipes
import com.fsof.project.model.repository.RecipeRepository
import com.fsof.project.view.activity.RecommendActivity

class RecommendFragment : Fragment() {

    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipeController: RecipeController
    private lateinit var recipeRepository: RecipeRepository
    private lateinit var ingredientsDatabase: IngredientDatabase

    private lateinit var glide: RequestManager

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
        ingredientsDatabase = IngredientDatabase.getInstance(requireContext())
        recipeRepository = RecipeRepository(RecipeClient.recipeService)
        recipeController = RecipeController(recipeRepository)

        glide = Glide.with(this)
        glide.load(R.raw.fire).override(650, 650).into(binding.fireGif)

        createRecipes(ingredientsDatabase.ingredientsDao().selectAll()) { recipes ->
            recipes.let {
                if (it != null) {
                    updateUI(it)
                    openDetails(it)
                } else {
                    Log.d("API", "recipes == null")
                    activity?.let {
                        Toast.makeText(it, "레시피 정보가 잘못되었어요. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            glide.clear(binding.fireGif)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openDetails(recipe: Recipes) {
        binding.morningText.setOnClickListener {
            navigateToDetail(recipe.breakfast.name, recipe.breakfast.ingredients, recipe.breakfast.detailedInstructions)
        }
        binding.noonText.setOnClickListener {
            navigateToDetail(recipe.lunch.name, recipe.lunch.ingredients, recipe.lunch.detailedInstructions)
        }
        binding.nightText.setOnClickListener {
            navigateToDetail(recipe.dinner.name, recipe.dinner.ingredients, recipe.dinner.detailedInstructions)
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

    private fun createRecipes(input: List<Ingredients>, callback: (Recipes?) -> Unit)/*: Recipes?*/ {
        var recipe: Recipes? = null
        recipeController.createRecipe(input) { results, throwable ->
            handler.post {
                if (throwable != null) {
                    Log.d("API", "Error: ${throwable.message}")
                    activity?.let { Toast.makeText(it, "레시피 생성 실패 ㅠㅠ", Toast.LENGTH_SHORT).show() }
                    callback(null)
                } else {
                    results?.let {
                        Log.d("API", "Recipes: $it")
                        recipe = it
                        callback(it)
                    } ?: run {
                        activity?.let {
                            Toast.makeText(it, "레시피 생성 실패 ㅠㅠ", Toast.LENGTH_SHORT).show()
                        }
                        callback(null)
                    }
                    Log.d("API", recipe.toString()) // binding.recipeText.text = recipes.toString() // binding.recipeText.setText(recipes.toString())
                }
            }
        }
        // return recipe
    }

    private fun updateUI(recipe: Recipes) {
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

        // Binding
        binding.morningText.text = recipe.breakfast.name
        binding.noonText.text = recipe.lunch.name
        binding.nightText.text = recipe.dinner.name
    }
}