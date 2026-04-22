package com.biathlonapp.ui.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.biathlonapp.R
import com.biathlonapp.data.model.CalendarDay
import com.biathlonapp.databinding.ItemCalendarDayBinding
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter(
    private val onDayClick: (CalendarDay) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private var days = listOf<CalendarDay>()
    private val daysOfWeek = listOf("ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ", "ВС")

    fun submitList(newDays: List<CalendarDay>) {
        days = newDays
        notifyDataSetChanged()
    }

    override fun getItemCount() = days.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = ItemCalendarDayBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CalendarViewHolder(binding, onDayClick)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(days[position])
    }

    fun getDayOfWeekHeader(): List<String> = daysOfWeek

    inner class CalendarViewHolder(
        private val binding: ItemCalendarDayBinding,
        private val onDayClick: (CalendarDay) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(day: CalendarDay) {
            binding.textDay.text = day.dayOfMonth.toString()

            // Получаем цвет текста в зависимости от темы
            val textColor = ContextCompat.getColor(binding.root.context, R.color.primary_text)

            if (!day.isCurrentMonth) {
                binding.textDay.alpha = 0.3f
                binding.textDay.setTextColor(textColor)
            } else {
                binding.textDay.alpha = 1.0f
                binding.textDay.setTextColor(textColor)
            }

            // Управление видимостью точек
            when {
                day.hasMixedEvent -> {
                    // Смешанная эстафета - голубая точка
                    binding.viewMixedDot.visibility = View.VISIBLE
                    binding.viewMaleDot.visibility = View.GONE
                    binding.viewFemaleDot.visibility = View.GONE
                    binding.viewEventDot.visibility = View.GONE
                }
                day.hasMaleEvent && day.hasFemaleEvent -> {
                    // Обе точки
                    binding.viewMaleDot.visibility = View.VISIBLE
                    binding.viewFemaleDot.visibility = View.VISIBLE
                    binding.viewMixedDot.visibility = View.GONE
                    binding.viewEventDot.visibility = View.GONE
                }
                day.hasMaleEvent -> {
                    // Только мужская
                    binding.viewMaleDot.visibility = View.VISIBLE
                    binding.viewFemaleDot.visibility = View.GONE
                    binding.viewMixedDot.visibility = View.GONE
                    binding.viewEventDot.visibility = View.GONE
                }
                day.hasFemaleEvent -> {
                    // Только женская
                    binding.viewMaleDot.visibility = View.GONE
                    binding.viewFemaleDot.visibility = View.VISIBLE
                    binding.viewMixedDot.visibility = View.GONE
                    binding.viewEventDot.visibility = View.GONE
                }
                day.hasEvent -> {
                    // Обычная точка
                    binding.viewMaleDot.visibility = View.GONE
                    binding.viewFemaleDot.visibility = View.GONE
                    binding.viewMixedDot.visibility = View.GONE
                    binding.viewEventDot.visibility = View.VISIBLE
                }
                else -> {
                    // Нет событий
                    binding.viewMaleDot.visibility = View.GONE
                    binding.viewFemaleDot.visibility = View.GONE
                    binding.viewMixedDot.visibility = View.GONE
                    binding.viewEventDot.visibility = View.GONE
                }
            }

            // Проверка на сегодня
            val calendar = Calendar.getInstance()
            val today = calendar.get(Calendar.DAY_OF_MONTH)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentYear = calendar.get(Calendar.YEAR)

            val dayCalendar = Calendar.getInstance()
            dayCalendar.time = day.date
            val isToday = dayCalendar.get(Calendar.DAY_OF_MONTH) == today &&
                    dayCalendar.get(Calendar.MONTH) == currentMonth &&
                    dayCalendar.get(Calendar.YEAR) == currentYear &&
                    day.isCurrentMonth

            // Установка цвета фона карточки (только для сегодняшнего дня)
            if (isToday) {
                binding.cardDay.setCardBackgroundColor(
                    ContextCompat.getColor(binding.root.context, R.color.calendar_today_bg)
                )
            } else {
                // Используем цвет фона из темы
                binding.cardDay.setCardBackgroundColor(
                    ContextCompat.getColor(binding.root.context, R.color.card_background)
                )
            }

            binding.cardDay.setOnClickListener {
                if (day.isCurrentMonth) onDayClick(day)
            }
        }
    }
}