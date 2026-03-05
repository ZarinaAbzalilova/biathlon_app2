package com.biathlonapp.ui.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biathlonapp.data.model.TeamItem
import com.biathlonapp.databinding.ItemTeamHeaderBinding
import com.biathlonapp.databinding.ItemTeamCategoryBinding

class TeamSectionAdapter(
    private val onCategoryClick: (TeamItem.Category) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<TeamItem>()

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CATEGORY = 1
    }

    fun submitList(newItems: List<TeamItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is TeamItem.Header -> TYPE_HEADER
            is TeamItem.Category -> TYPE_CATEGORY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val binding = ItemTeamHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                HeaderViewHolder(binding)
            }
            TYPE_CATEGORY -> {
                val binding = ItemTeamCategoryBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                CategoryViewHolder(binding, onCategoryClick)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is TeamItem.Header -> (holder as HeaderViewHolder).bind(item)
            is TeamItem.Category -> (holder as CategoryViewHolder).bind(item)
        }
    }

    override fun getItemCount() = items.size

    class HeaderViewHolder(
        private val binding: ItemTeamHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(header: TeamItem.Header) {
            binding.textHeader.text = header.title
        }
    }

    class CategoryViewHolder(
        private val binding: ItemTeamCategoryBinding,
        private val onCategoryClick: (TeamItem.Category) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: TeamItem.Category) {
            binding.textTitle.text = category.title


            // Устанавливаем цвет в зависимости от пола
            val color = if (category.gender == "male") {
                android.graphics.Color.parseColor("#2196F3") // Синий
            } else {
                android.graphics.Color.parseColor("#E91E63") // Розовый
            }


            binding.cardRoot.setOnClickListener {
                onCategoryClick(category)
            }
        }
    }
}