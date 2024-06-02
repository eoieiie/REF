package com.fsof.project.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fsof.project.databinding.ItemLayoutBinding
import com.fsof.project.model.entity.Ingredients
import com.fsof.project.R

class Adapter(
    private var dataList: List<Ingredients>,
    private val itemClickListener: (Ingredients, Boolean) -> Unit
) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private val selectedItems = mutableSetOf<Ingredients>()
    private val displayInfoList = mutableSetOf<Int>()
    private var isEditMode = false

    class ViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]

        // 아이템 선택 기능 추가
        holder.itemView.setOnClickListener {
            if (isEditMode) {
                val isSelected = selectedItems.contains(item)
                if (isSelected) {
                    selectedItems.remove(item)
                } else {
                    selectedItems.add(item)
                }
                itemClickListener(item, !isSelected)
                notifyItemChanged(position)
            } else {
                // 원래의 세부 정보 표시 기능
                if (displayInfoList.contains(position)) {
                    displayInfoList.remove(position)
                } else {
                    displayInfoList.add(position)
                }
                notifyItemChanged(position)
            }
        }

        // 텍스트 설정
        holder.binding.textView.text = if (displayInfoList.contains(position)) {
            "칼로리: ${item.nutrients.calories}kcal\n탄수화물: ${item.nutrients.carbohydrates}g\n단백질: ${item.nutrients.protein}g\n지방: ${item.nutrients.fat}g"
        } else {
            "${item.name}\n${item.weight}\n${item.expiration_date}"
        }

        // 선택된 아이템 표시
        if (isEditMode) {
            holder.binding.root.setBackgroundResource(
                if (selectedItems.contains(item)) R.drawable.card_background_selected
                else R.drawable.card_background_s
            )
        } else {
            // isFreezed 값에 따라 배경 설정 (기본 배경)
            if (item.isFreezed) {
                holder.binding.root.setBackgroundResource(R.drawable.card_background)
            } else {
                holder.binding.root.setBackgroundResource(R.drawable.card_background_nengjang)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setEditMode(isEditMode: Boolean) {
        this.isEditMode = isEditMode
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun getSelectedItems(): List<Ingredients> {
        return selectedItems.toList()
    }

    fun updateData(newIngredients: List<Ingredients>) {
        this.dataList = newIngredients
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun toggleSelection(ingredient: Ingredients, isChecked: Boolean) {
        if (isChecked) {
            selectedItems.add(ingredient)
        } else {
            selectedItems.remove(ingredient)
        }
    }
}
