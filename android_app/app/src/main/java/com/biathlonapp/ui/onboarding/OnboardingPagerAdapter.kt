package com.biathlonapp.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biathlonapp.databinding.OnboardingItemBinding
import com.bumptech.glide.Glide

data class OnboardingItem(
    val title: String,
    val description: String,
    val imageRes: Int,
    val gifRes: Int? = null
)

class OnboardingPagerAdapter(
    private val items: List<OnboardingItem>
) : RecyclerView.Adapter<OnboardingPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OnboardingItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        private val binding: OnboardingItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OnboardingItem, position: Int) {
            // Устанавливаем заголовок и описание
            binding.textTitle.text = item.title
            binding.textDescription.text = item.description

            // Устанавливаем картинку (логотип для 1 экрана, иконки для остальных)
            binding.imageIcon.setImageResource(item.imageRes)

            // Для первого экрана показываем GIF
            if (position == 0 && item.gifRes != null) {
                binding.gifImage.visibility = android.view.View.VISIBLE

                // Устанавливаем GIF с указанием размеров в коде
                val layoutParams = binding.gifImage.layoutParams
                layoutParams.width = 1000  // ширина в dp (примерно 280)
                layoutParams.height = 400 // высота в dp (примерно 140)
                binding.gifImage.layoutParams = layoutParams

                Glide.with(binding.root.context)
                    .asGif()
                    .load(item.gifRes)
                    .into(binding.gifImage)
            } else {
                binding.gifImage.visibility = android.view.View.GONE
            }
        }
    }
}