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

            // Добавляем индикатор пола
            when (event.gender) {
                "М" -> {
                    binding.textGender.text = "Мужчины"
                    binding.textGender.setTextColor(Color.WHITE)
                    binding.genderBadge.setCardBackgroundColor(Color.parseColor("#2196F3")) // Синий
                    binding.cardEvent.setCardBackgroundColor(Color.parseColor("#E3F2FD")) // Светло-синий фон
                }
                "Ж" -> {
                    binding.textGender.text = "Женщины"
                    binding.textGender.setTextColor(Color.WHITE)
                    binding.genderBadge.setCardBackgroundColor(Color.parseColor("#E91E63")) // Розовый
                    binding.cardEvent.setCardBackgroundColor(Color.parseColor("#FCE4EC")) // Светло-розовый фон
                }
                else -> {
                    binding.textGender.text = ""
                    binding.genderBadge.visibility = View.GONE
                    binding.cardEvent.setCardBackgroundColor(Color.WHITE)
                }
            }

            // Форматируем время, если нужно
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

            itemView.setOnClickListener {
                onEventClick(event)
            }
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