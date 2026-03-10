package com.biathlonapp.ui.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biathlonapp.data.model.Athlete
import com.biathlonapp.databinding.ItemTeamAthleteBinding
import java.text.SimpleDateFormat
import java.util.*

class TeamAthleteAdapter(
    private val onAthleteClick: (Athlete) -> Unit
) : RecyclerView.Adapter<TeamAthleteAdapter.TeamAthleteViewHolder>() {

    private var athletes: List<Athlete> = emptyList()

    fun submitList(newAthletes: List<Athlete>) {
        athletes = newAthletes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamAthleteViewHolder {
        val binding = ItemTeamAthleteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TeamAthleteViewHolder(binding, onAthleteClick)
    }

    override fun onBindViewHolder(holder: TeamAthleteViewHolder, position: Int) {
        if (position < athletes.size) {
            holder.bind(athletes[position])
        }
    }

    override fun getItemCount() = athletes.size

    class TeamAthleteViewHolder(
        private val binding: ItemTeamAthleteBinding,
        private val onAthleteClick: (Athlete) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(athlete: Athlete) {
            binding.textAthleteName.text = "${athlete.lastName ?: ""} ${athlete.firstName ?: ""}".trim()
            binding.textAthleteRegion.text = athlete.region ?: ""
            binding.textAthleteRank.text = athlete.sportsRank ?: ""

            athlete.birthDate?.let {
                binding.textAthleteAge.text = "Возраст: ${calculateAge(it)}"
            } ?: run {
                binding.textAthleteAge.text = ""
            }

            itemView.setOnClickListener {
                onAthleteClick(athlete)
            }
        }

        private fun calculateAge(birthDate: String): String {
            return try {
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val birth = format.parse(birthDate)
                val today = Calendar.getInstance()
                val cal = Calendar.getInstance()
                cal.time = birth
                val age = today.get(Calendar.YEAR) - cal.get(Calendar.YEAR)
                "$age лет"
            } catch (e: Exception) {
                ""
            }
        }
    }
}