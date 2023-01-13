package com.example.ranfomfactnumbers.numbers.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.ranfomfactnumbers.databinding.ItemNumberBinding

class NumbersAdapter(
    private val clickListener: ClickListener
) : ListAdapter<NumberUi, NumberViewHolder>(NumberDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val binding = ItemNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NumberViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class NumberViewHolder(
    private val binding: ItemNumberBinding,
    private val clockListener: ClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: NumberUi) {
        model.map(binding.title, binding.subtitle)
        binding.root.setOnClickListener { clockListener.click(model) }
    }
}

class NumberDiffUtil : DiffUtil.ItemCallback<NumberUi>() {

    override fun areItemsTheSame(oldItem: NumberUi, newItem: NumberUi) = oldItem.map(newItem)

    override fun areContentsTheSame(oldItem: NumberUi, newItem: NumberUi) = oldItem == newItem

}

interface ClickListener {

    fun click(item: NumberUi)

}