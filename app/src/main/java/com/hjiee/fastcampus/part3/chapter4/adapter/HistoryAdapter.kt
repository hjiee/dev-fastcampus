package com.hjiee.fastcampus.part3.chapter4.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hjiee.fastcampus.databinding.ItemHistoryBinding
import com.hjiee.fastcampus.part3.chapter4.model.BookHistory

class HistoryAdapter(val historyDeleteClickListener: (String) -> (Unit)) : ListAdapter<BookHistory, HistoryAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(historyModel: BookHistory) {
            binding.historyKeywordTextView.text = historyModel.keyword
            binding.historyKeywordDeleteButton.setOnClickListener {
                historyDeleteClickListener(historyModel.keyword.orEmpty())
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BookHistory>() {
            override fun areContentsTheSame(oldItem: BookHistory, newItem: BookHistory) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: BookHistory, newItem: BookHistory) =
                oldItem.uid == newItem.uid
        }
    }

}