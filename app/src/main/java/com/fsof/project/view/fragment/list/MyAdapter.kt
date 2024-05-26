package com.fsof.project.view.fragment.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fsof.project.R
import com.fsof.project.model.entity.Ingredients

class MyAdapter(private val dataList: List<Ingredients>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

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
        holder.textView.text = if (displayInfoList.contains(position)) {
            "Calories: ${item.nutrients.calories}, Carbs: ${item.nutrients.carbohydrates}, Protein: ${item.nutrients.protein}, Fat: ${item.nutrients.fat}"
        } else {
            item.name
        }

        holder.itemView.setOnClickListener {
            if (displayInfoList.contains(position)) {
                displayInfoList.remove(position)
            } else {
                displayInfoList.add(position)
            }
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
