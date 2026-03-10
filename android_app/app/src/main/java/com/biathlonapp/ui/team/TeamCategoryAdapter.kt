package com.biathlonapp.ui.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biathlonapp.databinding.ItemTeamCategoryBinding

class TeamCategoryAdapter(
    private val onCategoryClick: (TeamCategory) -> Unit
) : RecyclerView.Adapter<TeamCategoryAdapter.CategoryViewHolder>() {

    private var categories: List<TeamCategory> = emptyList()

    fun submitList(newCategories: List<TeamCategory>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemTeamCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding, onCategoryClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

    class CategoryViewHolder(
        private val binding: ItemTeamCategoryBinding,
        private val onCategoryClick: (TeamCategory) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: TeamCategory) {
            binding.textCategoryTitle.text = category.title
            itemView.setOnClickListener {
                onCategoryClick(category)
            }
        }
    }
}