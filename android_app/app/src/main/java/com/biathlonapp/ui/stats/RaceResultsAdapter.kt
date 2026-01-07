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
            binding.textRaceId.text = result.raceId
            binding.textFinishPlace.text = "Место: ${result.finishPlace ?: "N/A"}"
            binding.textMissCount.text = "Промахи: ${result.missCount ?: "N/A"}"

            // Используем pdf_url из данных (в JSON он приходит как pdf_url, но в Kotlin мы используем pdfUrl)
            if (!result.pdfUrl.isNullOrEmpty()) {
                binding.buttonDownloadPdf.visibility = View.VISIBLE
                binding.buttonDownloadPdf.setOnClickListener {
                    onPdfDownloadClick(result)
                }
            } else {
                binding.buttonDownloadPdf.visibility = View.GONE
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<RaceResult>() {
        override fun areItemsTheSame(oldItem: RaceResult, newItem: RaceResult): Boolean {
            return oldItem.raceId == newItem.raceId
        }

        override fun areContentsTheSame(oldItem: RaceResult, newItem: RaceResult): Boolean {
            return oldItem == newItem
        }
    }
}