package com.biathlonapp.ui.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.biathlonapp.databinding.ItemRaceResultBinding

class RaceResultsAdapter(
    private val onPdfDownloadClick: (String) -> Unit  // Теперь принимает raceId или pdfUrl
) : ListAdapter<RaceResultDisplay, RaceResultsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRaceResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
    }

    inner class ViewHolder(private val binding: ItemRaceResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: RaceResultDisplay) {
            binding.textRaceName.text = result.nameRace.ifBlank { "Неизвестная гонка" }
            binding.textRacePlace.text = result.placeRace.ifBlank { "Место не указано" }
            binding.textDateDiscipline.text = "${result.date} • ${result.discipline}"

            binding.textFinishPlace.text = "Место: ${result.finishPlace ?: "N/A"}"
            binding.textMissCount.text = "Промахи: ${result.missCount ?: "N/A"}"
            binding.textStartNumber.text = "Стартовый номер: ${result.startNumber ?: "N/A"}"

            // PDF кнопка пока скрыта, так как у нас нет pdfUrl в этом классе
            binding.buttonDownloadPdf.visibility = android.view.View.GONE
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<RaceResultDisplay>() {
        override fun areItemsTheSame(oldItem: RaceResultDisplay, newItem: RaceResultDisplay): Boolean {
            return oldItem.nameRace == newItem.nameRace && oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: RaceResultDisplay, newItem: RaceResultDisplay): Boolean {
            return oldItem == newItem
        }
    }
}