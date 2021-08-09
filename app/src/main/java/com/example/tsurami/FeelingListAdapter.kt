package com.example.tsurami

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tsurami.FeelingListAdapter.FeelingViewHolder
import timber.log.Timber

class FeelingListAdapter : ListAdapter<Feeling, FeelingViewHolder>(FEELINGS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeelingViewHolder {
        Timber.d("\\[:onCreateViewHolder]")
        Timber.d("\\:FeelingViewMolder.create(parent)")
        Timber.d(";")
        return FeelingViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FeelingViewHolder, position: Int) {
        Timber.d("\\[:onBindViewHolder]")
        val current = getItem(position)
//        holder.bind(current.name)
        holder.bind(current.toString())
        Timber.d(";")
    }

    class FeelingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val feelingItemView = itemView.findViewById<TextView>(R.id.textView)

        fun bind(text: String?) {
            Timber.d("\\[:bind]")
            feelingItemView.text = text
            Timber.d(";")
        }

        companion object {
            fun create(parent: ViewGroup): FeelingViewHolder {
                Timber.d("\\[:create]")
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                Timber.d(";")
                return FeelingViewHolder(view)
            }
        }
    }

    companion object {
        private val FEELINGS_COMPARATOR = object : DiffUtil.ItemCallback<Feeling>() {
            override fun areItemsTheSame(oldItem: Feeling, newItem: Feeling): Boolean {
                Timber.d("\\[:areItemTheSame]")
                Timber.d(";")
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Feeling, newItem: Feeling): Boolean {
                Timber.d("\\[:areContentsTheSame]")
                Timber.d(";")
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}