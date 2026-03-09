package com.biathlonapp.ui.stats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.biathlonapp.data.model.RaceResult
import com.biathlonapp.databinding.ItemRaceResultBinding

class RaceResultsAdapter(
    private val onPdfDownloadClick: (RaceResult) -> Unit
) : ListAdapter<RaceResult, RaceResultsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRaceResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
    }

    inner class ViewHolder(private val binding: ItemRaceResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: RaceResult) {
            // Вместо race_id показываем название гонки
            binding.textRaceName.text = result.raceInfo?.nameRace ?: "Неизвестная гонка"

            // Место проведения (если есть)
            result.raceInfo?.placeRace?.let { place ->
                binding.textRacePlace.text = place
                binding.textRacePlace.visibility = View.VISIBLE
            } ?: run {
                binding.textRacePlace.visibility = View.GONE
            }

            // Дата и дисциплина
            binding.textDateDiscipline.text = buildString {
                result.raceInfo?.date?.let { append(it) }
                result.raceInfo?.discipline?.let {
                    if (isNotEmpty()) append(" • ")
                    append(formatDiscipline(it))
                }
            }

            // Результаты из athlete_performance
            binding.textFinishPlace.text = "Место: ${result.athletePerformance?.finishPlace ?: "N/A"}"
            binding.textMissCount.text = "Промахи: ${result.athletePerformance?.missCount ?: "N/A"}"
            binding.textStartNumber.text = "Стартовый номер: ${result.athletePerformance?.startNumber ?: "N/A"}"

            // Используем pdf_url из данных
            if (!result.pdfUrl.isNullOrEmpty()) {
                binding.buttonDownloadPdf.visibility = View.VISIBLE
                binding.buttonDownloadPdf.setOnClickListener {
                    onPdfDownloadClick(result)
                }
            } else {
                binding.buttonDownloadPdf.visibility = View.GONE
            }
        }

        private fun formatDiscipline(discipline: String?): String {
            return when (discipline) {
                "BT_Sprint" -> "Спринт"
                "BT_Pursuit" -> "Гонка преследования"
                "BT_Individual" -> "Индивидуальная"
                "BT_MassStart" -> "Масс-старт"
                "BT_Relay" -> "Эстафета"
                else -> discipline ?: ""
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<RaceResult>() {
        override fun areItemsTheSame(oldItem: RaceResult, newItem: RaceResult): Boolean {
            return oldItem.raceId == newItem.raceId
        }

        override fun areContentsTheSame(oldItem: RaceResult, newItem: RaceResult): Boolean {
            return oldItem.raceInfo?.nameRace == newItem.raceInfo?.nameRace &&
                    oldItem.athletePerformance?.finishPlace == newItem.athletePerformance?.finishPlace &&
                    oldItem.athletePerformance?.missCount == newItem.athletePerformance?.missCount &&
                    oldItem.pdfUrl == newItem.pdfUrl
        }
    }
}