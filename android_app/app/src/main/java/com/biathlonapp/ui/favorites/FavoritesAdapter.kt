package com.biathlonapp.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biathlonapp.data.local.FavoriteAthlete
import com.biathlonapp.databinding.ItemFavoriteAthleteBinding

class FavoritesAdapter(
    private val onItemClick: (FavoriteAthlete) -> Unit,
    private val onRemoveClick: (FavoriteAthlete) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    private var favorites = listOf<FavoriteAthlete>()

    fun submitList(newList: List<FavoriteAthlete>) {
        favorites = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteAthleteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(binding, onItemClick, onRemoveClick)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    override fun getItemCount() = favorites.size

    class FavoriteViewHolder(
        private val binding: ItemFavoriteAthleteBinding,
        private val onItemClick: (FavoriteAthlete) -> Unit,
        private val onRemoveClick: (FavoriteAthlete) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(favorite: FavoriteAthlete) {
            // Используем surname (фамилия) и name (имя)
            binding.textName.text = "${favorite.surname} ${favorite.name}".trim()
            binding.textSportsRank.text = favorite.sportsRank
            binding.textRegion.text = favorite.region ?: "Регион не указан"

            // Устанавливаем иконку в зависимости от пола
            if (favorite.gender.lowercase() == "male") {
                binding.imageAthlete.setImageResource(com.biathlonapp.R.drawable.ic_male)
            } else {
                binding.imageAthlete.setImageResource(com.biathlonapp.R.drawable.ic_female)
            }

            // Клик на всю карточку
            binding.cardFavorite.setOnClickListener {
                onItemClick(favorite)
            }

            // Клик на кнопку удаления
            binding.buttonRemove.setOnClickListener {
                onRemoveClick(favorite)
            }
        }
    }
}