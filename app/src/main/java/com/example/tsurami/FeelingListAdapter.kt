package com.example.tsurami

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tsurami.FeelingListAdapter.FeelingViewHolder
import com.example.tsurami.db.entity.Feeling
import timber.log.Timber

class FeelingListAdapter : ListAdapter<Feeling, FeelingViewHolder>(FEELINGS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeelingViewHolder {
        Timber.d("\\[:onCreateViewHolder]")
        Timber.d("\\:create FeelingViewHolder")
        Timber.d("\\:onCreateViewHolder end\n;")
        return FeelingViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FeelingViewHolder, position: Int) {
        Timber.d("\\[:onBindViewHolder]")
        Timber.d("\\:create Feeling!")
        val current = getItem(position)
//        holder.bind(current.name)
        Timber.d("\\:bind ")
        holder.bind(current.toString())
        Timber.d("\\:onBindViewHolder end\n;")
    }

    class FeelingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val feelingItemView = itemView.findViewById<TextView>(R.id.textView)

        fun bind(text: String?) {
            Timber.d("\\[:bind]")
            Timber.d("\\:")
            feelingItemView.text = text
            Timber.d("\\:bind end\n;")
        }

        companion object {
            fun create(parent: ViewGroup): FeelingViewHolder {
                Timber.d("\\[:create]")
                Timber.d("\\:get View from parent.context")
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                Timber.d("\\:create <FeelingViewHolder>")
                Timber.d("\\:create end\n;")
                return FeelingViewHolder(view)
            }
        }
    }

    companion object {
        private val FEELINGS_COMPARATOR = object : DiffUtil.ItemCallback<Feeling>() {
            override fun areItemsTheSame(oldItem: Feeling, newItem: Feeling): Boolean {
                Timber.d("\\[:areItemTheSame]")
                Timber.d("\\:return compare result")
                Timber.d("\\:areItemTheSame end\n;")
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Feeling, newItem: Feeling): Boolean {
                Timber.d("\\[:areContentsTheSame]")
                Timber.d("\\:return compare result")
                Timber.d("\\:areContentsTheSame end\n;")
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}