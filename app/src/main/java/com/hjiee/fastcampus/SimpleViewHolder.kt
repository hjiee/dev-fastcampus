package com.hjiee.fastcampus

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class SimpleViewHolder constructor(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val tvTitle: TextView by lazy { itemView.findViewById(R.id.tv_title) }
    private val tvPosition: TextView by lazy { itemView.findViewById(R.id.tv_position) }
    private val ivPin: ImageView by lazy { itemView.findViewById(R.id.iv_pin) }

    fun bind(item: Contents, position: Int) {
        tvPosition.text = (position + 1).toString()
        tvTitle.text = item.title
        ivPin.isVisible = item.isTop
    }
}