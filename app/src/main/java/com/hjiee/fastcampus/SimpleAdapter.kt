package com.hjiee.fastcampus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SimpleAdapter constructor(
        private val list: MutableList<Contents> = arrayListOf(),
        private val clickListener: ItemClickListener
) : RecyclerView.Adapter<SimpleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contents, parent, false)
        val holder = SimpleViewHolder(view)
        view.setOnClickListener {
            clickListener.onClick(holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    fun addItem(item: Contents) {
        list.add(item)
    }
}