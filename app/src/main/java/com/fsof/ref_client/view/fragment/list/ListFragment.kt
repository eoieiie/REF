package com.fsof.ref_client.view.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.fsof.ref_client.databinding.FragmentListBinding
import com.fsof.ref_client.model.datasource.IngredientDatabase
import com.fsof.ref_client.model.entity.Ingredients
import com.fsof.ref_client.view.adapter.Adapter

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: Adapter
    private lateinit var ingredientDatabase: IngredientDatabase

    private var isEditMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        ingredientDatabase = IngredientDatabase.getInstance(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)

        val ingredientList = ingredientDatabase.ingredientsDao().selectAll()
        adapter = Adapter(ingredientList) { ingredient, isChecked ->
            if (isEditMode) {
                handleItemSelection(ingredient, isChecked)
            } else {
                // 원래의 세부 정보 표시 기능
                showItemDetails(ingredient)
            }
        }
        binding.recyclerView.adapter = adapter

        // 수정 버튼 클릭 리스너 추가
        binding.btnSave.setOnClickListener {
            if (isEditMode) {
                deleteSelectedIngredients()
            } else {
                toggleEditMode()
            }
        }
    }

    private fun toggleEditMode() {
        isEditMode = !isEditMode
        adapter.setEditMode(isEditMode)
        if (isEditMode) {
            binding.btnSave.text = "삭제"
        } else {
            binding.btnSave.text = "수정"
        }
    }

    private fun handleItemSelection(ingredient: Ingredients, isChecked: Boolean) {
        adapter.toggleSelection(ingredient, isChecked)
    }

    private fun showItemDetails(ingredient: Ingredients) {
        // 세부 정보 표시 기능 구현
        // 예: 다이얼로그로 세부 정보를 표시하거나 새로운 프래그먼트를 열기
        AlertDialog.Builder(requireContext())
            .setTitle(ingredient.name)
            .setMessage("칼로리: ${ingredient.nutrients.calories}kcal\n탄수화물: ${ingredient.nutrients.carbohydrates}g\n단백질: ${ingredient.nutrients.protein}g\n지방: ${ingredient.nutrients.fat}g")
            .setPositiveButton("확인", null)
            .create()
            .show()
    }

    private fun deleteSelectedIngredients() {
        val selectedItems = adapter.getSelectedItems()
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("선택한 항목을 삭제하시겠습니까?")
            .setPositiveButton("네") { _, _ ->
                selectedItems.forEach { ingredient ->
                    ingredientDatabase.ingredientsDao().deleteData(ingredient)
                }
                refreshList()
                toggleEditMode() // 삭제 후 수정 모드 종료
            }
            .setNegativeButton("아니오", null)
            .create()

        // "네" 버튼의 텍스트 색상을 검정색으로 설정
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
        }

        dialog.show()
    }

    private fun refreshList() {
        val updatedList = ingredientDatabase.ingredientsDao().selectAll()
        adapter.updateData(updatedList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
