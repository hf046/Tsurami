package com.example.tsurami

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tsurami.FeelingListAdapter.FeelingViewHolder

class FeelingListAdapter : ListAdapter<Feeling, FeelingViewHolder>(FEELINGS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeelingViewHolder {
        return FeelingViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FeelingViewHolder, position: Int) {
        val current = getItem(position)
//        holder.bind(current.name)
        holder.bind(current.toString())
    }

    class FeelingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val feelingItemView = itemView.findViewById<TextView>(R.id.textView)

        fun bind(text: String?) {
            feelingItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): FeelingViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return FeelingViewHolder(view)
            }
        }
    }

    companion object {
        private val FEELINGS_COMPARATOR = object : DiffUtil.ItemCallback<Feeling>() {
            override fun areItemsTheSame(oldItem: Feeling, newItem: Feeling): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Feeling, newItem: Feeling): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}