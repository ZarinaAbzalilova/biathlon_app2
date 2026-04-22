package com.biathlonapp.ui.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.biathlonapp.R
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

            // Определяем цвет в зависимости от пола команды
            val colorRes = if (category.title.contains("женская", ignoreCase = true)) {
                R.color.pink_team  // #E91E63 для женских команд
            } else {
                R.color.blue_team  // #2196F3 для мужских команд
            }

            val color = ContextCompat.getColor(binding.root.context, colorRes)

            // Применяем цвет к обводке карточки
            binding.root.strokeColor = color

            // Применяем цвет к тексту
            binding.textCategoryTitle.setTextColor(color)

            itemView.setOnClickListener {
                onCategoryClick(category)
            }
        }
    }
}