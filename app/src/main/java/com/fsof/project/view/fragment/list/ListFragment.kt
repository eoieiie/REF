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
import com.fsof.project.model.room.IngredientsDao
import com.fsof.project.model.entity.Ingredients
import com.fsof.project.model.nutrients.Nutrients

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private lateinit var ingredientsDB: IngredientDatabase

    val dummyData= listOf(
        Ingredients("달걀", "1개", false, "24-05-25", "24-05-25", Nutrients(70, 0.6, 6.3, 4.8)),
        Ingredients("닭고기", "1개", false, "24-05-25", "24-05-25", Nutrients(215, 0.0, 43.0, 4.5)),
        Ingredients("대파", "", false, "24-05-26", "24-05-26", Nutrients(64, 15.0, 2.0, 0.0)),
        Ingredients("당근", "3개", false, "24-05-26", "24-05-26", Nutrients(105, 24.0, 2.0, 0.5)),
        Ingredients("새우", "1마리", false, "24-05-26", "24-05-26", Nutrients(7, 0.1, 1.5, 0.1))
    ) // ingredientsDB.ingredientsDao().selectAll()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        val testData = generateTestData() // 테스트 데이터 생성
        val adapter = MyAdapter(testData) // Adapter 생성

        recyclerView.adapter = adapter
    }

    private fun generateTestData(): List<ItemData> {
        val testData = mutableListOf<ItemData>()
        for (i in 1..20) {
            testData.add(ItemData(content = "Item $i"))
        }
        return testData
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}