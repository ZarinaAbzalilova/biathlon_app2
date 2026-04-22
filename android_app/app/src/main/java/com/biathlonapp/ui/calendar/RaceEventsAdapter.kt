package com.biathlonapp.ui.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.biathlonapp.data.model.RaceEvent
import com.biathlonapp.databinding.ItemRaceEventBinding
import java.text.SimpleDateFormat
import java.util.*

class RaceEventsAdapter(
    private val onEventClick: (RaceEvent) -> Unit
) : RecyclerView.Adapter<RaceEventsAdapter.EventViewHolder>() {

    private var events = listOf<RaceEvent>()
    private val timeFormat = SimpleDateFormat("HH:mm", Locale("ru"))

    fun submitList(newEvents: List<RaceEvent>) {
        events = newEvents
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemRaceEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EventViewHolder(binding, onEventClick)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount() = events.size

    class EventViewHolder(
        private val binding: ItemRaceEventBinding,
        private val onEventClick: (RaceEvent) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: RaceEvent) {
            binding.textTitle.text = event.title
            binding.textDiscipline.text = formatDiscipline(event.discipline)
            binding.textLocation.text = event.location

            when (event.gender) {
                "М", "мужской", "male", "Мужчины" -> {
                    binding.textGender.text = "Мужчины"
                    binding.genderBadge.setCardBackgroundColor(android.graphics.Color.parseColor("#2196F3"))
                    binding.textGender.setTextColor(android.graphics.Color.WHITE)
                }
                "Ж", "женский", "female", "Женщины" -> {
                    binding.textGender.text = "Женщины"
                    binding.genderBadge.setCardBackgroundColor(android.graphics.Color.parseColor("#E91E63"))
                    binding.textGender.setTextColor(android.graphics.Color.WHITE)
                }
                "Смешанная" -> {
                    binding.textGender.text = "Смешанная"
                    binding.genderBadge.setCardBackgroundColor(android.graphics.Color.parseColor("#00BCD4"))
                    binding.textGender.setTextColor(android.graphics.Color.WHITE)
                }
                else -> {
                    binding.textGender.text = event.gender ?: "?"
                    binding.genderBadge.setCardBackgroundColor(android.graphics.Color.parseColor("#9E9E9E"))
                    binding.textGender.setTextColor(android.graphics.Color.WHITE)
                }
            }

            // Форматируем время
            val calendar = Calendar.getInstance()
            calendar.time = event.date
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            if (hour > 0 || minute > 0) {
                binding.textTime.text = String.format("%02d:%02d", hour, minute)
                binding.textTime.visibility = View.VISIBLE
            } else {
                binding.textTime.visibility = View.GONE
            }

            itemView.setOnClickListener { onEventClick(event) }
        }

        private fun formatDiscipline(discipline: String?): String {
            return when (discipline) {
                "BT_Sprint" -> "Спринт"
                "BT_Pursuit" -> "Гонка преследования"
                "BT_Individual" -> "Индивидуальная"
                "BT_Mass" -> "Масс-старт"
                "BT_Mass60" -> "Масс-старт 60"
                "BT_Relay" -> "Эстафета"
                "BT_SingleMixedRelay" -> "Одиночная смешанная эстафета"
                "BT_SuperSprint" -> "Супер-спринт"
                "BT_MixedRelay" -> "Смешанная эстафета"
                "BT_SingleRelay" -> "Одиночная эстафета"
                "BT_Marathon" -> "Марафон"
                "BT_SuperPursuit" -> "Супер-пасьют"
                else -> discipline ?: ""
            }
        }
    }
}