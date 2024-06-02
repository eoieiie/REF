package com.fsof.project.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fsof.project.model.entity.Ingredients
import com.fsof.project.R

class Adapter(private val dataList: List<Ingredients>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

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
        holder.textView.text = "${item.name}\n${item.weight}\n${item.expiration_date}"

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
