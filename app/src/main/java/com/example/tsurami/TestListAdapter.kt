package com.example.tsurami

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tsurami.db.entity.Test

class TestListAdapter : ListAdapter<Test, TestListAdapter.TestViewHolder>(TestsComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.text)
    }

    class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val testItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            testItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): TestViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return TestViewHolder(view)
            }
        }
    }

    class TestsComparator : DiffUtil.ItemCallback<Test>() {
        override fun areItemsTheSame(oldItem: Test, newItem: Test): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Test, newItem: Test): Boolean {
            return oldItem.text == newItem.text
        }
    }
}