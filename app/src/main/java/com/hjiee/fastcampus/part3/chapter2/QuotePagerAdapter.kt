package com.hjiee.fastcampus.part3.chapter2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hjiee.fastcampus.R

class QuotePagerAdapter(
    private val quotes: List<Quote>,
    private val isNameRevealed: Boolean
) : RecyclerView.Adapter<QuotePagerAdapter.QuoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val actualPosition = position % quotes.size
        holder.bind(quotes[actualPosition], isNameRevealed)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvQuote by lazy { itemView.findViewById<TextView>(R.id.tv_quote) }
        private val tvName by lazy { itemView.findViewById<TextView>(R.id.tv_name) }

        @SuppressLint("SetTextI18n")
        fun bind(quote: Quote, isNameRevealed: Boolean) {
            tvQuote.text = "\"${quote.quote}\""

            if (isNameRevealed) {
                tvName.text = "- ${quote.name}"
                tvName.visibility = View.VISIBLE
            } else {
                tvName.visibility = View.GONE
            }
        }
    }
}