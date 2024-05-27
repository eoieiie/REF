package com.fsof.project.view.fragment.recommend

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fsof.project.databinding.FragmentRecommendBinding

import com.fsof.project.controller.NutrientController
import com.fsof.project.controller.RecipeController
import com.fsof.project.controller.client.NutrientClient
import com.fsof.project.controller.client.RecipeClient
import com.fsof.project.model.room.IngredientDatabase
import com.fsof.project.model.room.RecipeDatabase
import com.fsof.project.model.entity.Ingredients

class RecommendFragment : Fragment() {

    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

    private lateinit var nutrientController: NutrientController
    private lateinit var recipeController: RecipeController

    private lateinit var recipeDatabase: RecipeDatabase
    private lateinit var ingredientsDatabase: IngredientDatabase

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
        nutrientController = NutrientController(NutrientClient.nutrientService)

        recipeDatabase = RecipeDatabase.getInstance(requireContext())
        recipeController = RecipeController(RecipeClient.recipeService)

//        binding.createRecipeBtn.setOnClickListener {
            createRecipes(ingredientsDatabase.ingredientsDao().selectAll())
//        }
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
                    activity?.let { Toast.makeText(it, "영양소 생성 실패 ㅠㅠ", Toast.LENGTH_SHORT).show() } // Toast.makeText(getActivity(), "영양소 생성 실패 ㅠㅠ", Toast.LENGTH_SHORT).show()
                } else if (recipes != null) {
                    Log.d("API", "Recipes: $recipes")
                    binding.recipeText.text = recipes.toString() // binding.recipeText.setText(recipes.toString())
                } else {
                    Log.d("API", "Unknown error occurred")
                    activity?.let { Toast.makeText(it, "영양소 생성 실패 ㅠㅠ", Toast.LENGTH_SHORT).show() } // Toast.makeText(getActivity(), "영양소 생성 실패 ㅠㅠ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}