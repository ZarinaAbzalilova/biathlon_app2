package com.biathlonapp.ui.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.biathlonapp.databinding.ItemRaceResultBinding
import java.text.SimpleDateFormat
import java.util.*

class RaceResultsAdapter(
    private val onPdfDownloadClick: (String) -> Unit,
    private val onRaceClick: (String) -> Unit
) : ListAdapter<RaceResultDisplay, RaceResultsAdapter.ViewHolder>(DiffCallback) {

    private val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val outputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale("ru"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRaceResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
    }

    private fun formatDate(dateString: String): String {
        return try {
            val date = inputDateFormat.parse(dateString)
            date?.let { outputDateFormat.format(it) } ?: dateString
        } catch (e: Exception) {
            dateString
        }
    }

    inner class ViewHolder(private val binding: ItemRaceResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: RaceResultDisplay) {
            binding.textRaceName.text = result.nameRace.ifBlank { "Неизвестная гонка" }
            binding.textRacePlace.text = result.placeRace.ifBlank { "Место не указано" }

            // Преобразуем дату в формат ДД.ММ.ГГГГ
            val formattedDate = formatDate(result.date)
            binding.textDateDiscipline.text = formattedDate

            binding.textFinishPlace.text = "Место: ${result.finishPlace ?: "N/A"}"
            binding.textMissCount.text = "Промахи: ${result.missCount ?: "N/A"}"
            binding.textStartNumber.text = "Стартовый номер: ${result.startNumber ?: "N/A"}"

            itemView.setOnClickListener {
                result.raceId?.let { onRaceClick(it) }
            }

            // Показываем кнопку PDF если есть ссылка
            if (!result.pdfUrl.isNullOrBlank()) {
                binding.buttonDownloadPdf.visibility = android.view.View.VISIBLE
                binding.buttonDownloadPdf.setOnClickListener {
                    onPdfDownloadClick(result.pdfUrl)
                }
            } else {
                binding.buttonDownloadPdf.visibility = android.view.View.GONE
            }
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