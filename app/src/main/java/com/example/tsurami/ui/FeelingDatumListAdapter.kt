package com.example.tsurami

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tsurami.db.datum.FeelingDatum

class FeelingDatumListAdapter
    : ListAdapter<FeelingDatum, FeelingDatumListAdapter.FeelingDatumViewHolder>
    (FEELING_DATUM_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeelingDatumViewHolder {
        return FeelingDatumViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FeelingDatumViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.toString())
    }

    class FeelingDatumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val feelingDatumItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            feelingDatumItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): FeelingDatumViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return FeelingDatumViewHolder(view)
            }
        }
    }

    companion object {
        private val FEELING_DATUM_COMPARATOR = object : DiffUtil.ItemCallback<FeelingDatum>() {
            override fun areItemsTheSame(oldItem: FeelingDatum, newItem: FeelingDatum): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: FeelingDatum, newItem: FeelingDatum): Boolean {
//                適当
                return oldItem == newItem
            }
        }
    }
}