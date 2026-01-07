package com.biathlonapp.ui.athletes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.biathlonapp.data.model.Athlete
import com.biathlonapp.databinding.ItemAthleteBinding

class AthletesAdapter(
    private val onItemClick: (Athlete) -> Unit
) : ListAdapter<Athlete, AthletesAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAthleteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val athlete = getItem(position)
        holder.bind(athlete)
    }

    inner class ViewHolder(private val binding: ItemAthleteBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val athlete = getItem(adapterPosition)
                onItemClick(athlete)
            }
        }

        fun bind(athlete: Athlete) {
            binding.textAthleteName.text = "${athlete.lastName} ${athlete.firstName}"
            binding.textAthleteRegion.text = athlete.region
            binding.textAthleteGender.text = athlete.gender
            binding.textAthleteRank.text = athlete.sportsRank

            // Если у вас есть поле club или regionCode, используйте его
            // binding.textAthleteClub.text = athlete.regionCode ?: ""
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Athlete>() {
        override fun areItemsTheSame(oldItem: Athlete, newItem: Athlete): Boolean {
            return oldItem.athleteId == newItem.athleteId
        }

        override fun areContentsTheSame(oldItem: Athlete, newItem: Athlete): Boolean {
            return oldItem == newItem
        }
    }
}