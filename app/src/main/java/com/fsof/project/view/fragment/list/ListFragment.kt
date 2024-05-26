package com.fsof.project.view.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fsof.project.databinding.FragmentListBinding

import com.fsof.project.model.room.IngredientDatabase
//import com.fsof.project.model.entity.Ingredients
//import com.fsof.project.model.nutrients.Nutrients

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter

    private lateinit var ingredientsDatabase: IngredientDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        ingredientsDatabase = IngredientDatabase.getInstance(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        val ingredientList = ingredientsDatabase.ingredientsDao().selectAll()
        adapter = Adapter(ingredientList)
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun generateTestData(): List<Ingredients> {
//        return listOf(
//            Ingredients(name = "달걀", weight = "1개", isFreezed = false, up_date = "24-05-25", expiration_date = "24-05-25", nutrients = Nutrients(calories = 70, carbohydrates = 0.6, protein = 6.3, fat = 4.8)),
//            Ingredients(name = "닭고기", weight = "1개", isFreezed = false, up_date = "24-05-25", expiration_date = "24-05-25", nutrients = Nutrients(calories = 215, carbohydrates = 0.0, protein = 43.0, fat = 4.5)),
//            Ingredients(name = "대파", weight = "", isFreezed = false, up_date = "24-05-26", expiration_date = "24-05-26", nutrients = Nutrients(calories = 64, carbohydrates = 15.0, protein = 2.0, fat = 0.0)),
//            Ingredients(name = "당근", weight = "3개", isFreezed = false, up_date = "24-05-26", expiration_date = "24-05-26", nutrients = Nutrients(calories = 105, carbohydrates = 24.0, protein = 2.0, fat = 0.5)),
//            Ingredients(name = "새우", weight = "1마리", isFreezed = false, up_date = "24-05-26", expiration_date = "24-05-26", nutrients = Nutrients(calories = 7, carbohydrates = 0.1, protein = 1.5, fat = 0.1))
//        )
//    }
}