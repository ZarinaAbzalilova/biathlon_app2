package com.biathlonapp.ui.raceprotocol

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biathlonapp.data.model.RaceResultItem
import com.biathlonapp.databinding.ItemRaceProtocolBinding

class RaceProtocolAdapter(
    private val onAthleteClick: (String) -> Unit
) : RecyclerView.Adapter<RaceProtocolAdapter.ViewHolder>() {

    private var results: List<RaceResultItem> = emptyList()

    fun submitList(newResults: List<RaceResultItem>) {
        results = newResults
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRaceProtocolBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onAthleteClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount() = results.size

    class ViewHolder(
        private val binding: ItemRaceProtocolBinding,
        private val onAthleteClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(result: RaceResultItem) {
            binding.textPlace.text = result.finishPlace?.toString() ?: "-"
            binding.textStartNumber.text = result.startNumber?.toString() ?: "-"
            binding.textAthleteName.text = "${result.lastName} ${result.firstName}"
            binding.textMissCount.text = result.missCount?.toString() ?: "-"
            binding.textFinishTime.text = result.finishTime ?: "-"

            binding.textRegion.text = result.region ?: ""
            binding.textSportsRank.text = result.sportsRank ?: ""

            itemView.setOnClickListener {
                onAthleteClick(result.athleteId)
            }
        }
    }
}