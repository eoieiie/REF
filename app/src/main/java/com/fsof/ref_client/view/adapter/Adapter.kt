package com.fsof.ref_client.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fsof.ref_client.model.entity.Ingredients
import com.fsof.ref_client.R

class Adapter(private val dataList: List<Ingredients>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    private val displayInfoList = mutableSetOf<Int>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]

        // 클릭 시 영양 정보 표시
        holder.itemView.setOnClickListener {
            if (displayInfoList.contains(position)) {
                displayInfoList.remove(position)
            } else {
                displayInfoList.add(position)
            }
            notifyItemChanged(position)
        }

        // 영양 정보 표시 여부에 따라 텍스트 설정
        holder.textView.text = if (displayInfoList.contains(position)) {
            "칼로리: ${item.nutrients.calories}kcal\n탄수화물: ${item.nutrients.carbohydrates}g\n단백질: ${item.nutrients.protein}g\n지방: ${item.nutrients.fat}g"
        } else {
            "${item.name}\n${item.weight}\n${item.expiration_date}"
        }

        // isFreezed 값에 따라 배경 설정
        if (item.isFreezed) {
            holder.itemView.setBackgroundResource(R.drawable.card_background)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.card_background_nengjang)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
