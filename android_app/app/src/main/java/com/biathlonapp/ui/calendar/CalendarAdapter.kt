package com.biathlonapp.ui.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

            if (!day.isCurrentMonth) {
                binding.textDay.alpha = 0.3f
            } else {
                binding.textDay.alpha = 1.0f
            }

            // Управление видимостью точек
            when {
                day.hasMixedEvent -> {
                    // Смешанная эстафета - голубая точка
                    binding.viewMixedDot.visibility = View.VISIBLE
                    binding.viewMaleDot.visibility = View.GONE
                    binding.viewFemaleDot.visibility = View.GONE
                    binding.viewEventDot.visibility = View.GONE
                    binding.textDay.setTextColor(Color.BLACK)
                }
                day.hasMaleEvent && day.hasFemaleEvent -> {
                    // Обе точки
                    binding.viewMaleDot.visibility = View.VISIBLE
                    binding.viewFemaleDot.visibility = View.VISIBLE
                    binding.viewMixedDot.visibility = View.GONE
                    binding.viewEventDot.visibility = View.GONE
                    binding.textDay.setTextColor(Color.BLACK)
                }
                day.hasMaleEvent -> {
                    // Только мужская
                    binding.viewMaleDot.visibility = View.VISIBLE
                    binding.viewFemaleDot.visibility = View.GONE
                    binding.viewMixedDot.visibility = View.GONE
                    binding.viewEventDot.visibility = View.GONE
                    binding.textDay.setTextColor(Color.BLACK)
                }
                day.hasFemaleEvent -> {
                    // Только женская
                    binding.viewMaleDot.visibility = View.GONE
                    binding.viewFemaleDot.visibility = View.VISIBLE
                    binding.viewMixedDot.visibility = View.GONE
                    binding.viewEventDot.visibility = View.GONE
                    binding.textDay.setTextColor(Color.BLACK)
                }
                day.hasEvent -> {
                    // Обычная точка
                    binding.viewMaleDot.visibility = View.GONE
                    binding.viewFemaleDot.visibility = View.GONE
                    binding.viewMixedDot.visibility = View.GONE
                    binding.viewEventDot.visibility = View.VISIBLE
                    binding.textDay.setTextColor(Color.parseColor("#2196F3"))
                }
                else -> {
                    // Нет событий
                    binding.viewMaleDot.visibility = View.GONE
                    binding.viewFemaleDot.visibility = View.GONE
                    binding.viewMixedDot.visibility = View.GONE
                    binding.viewEventDot.visibility = View.GONE
                    binding.textDay.setTextColor(Color.BLACK)
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

            binding.cardDay.setCardBackgroundColor(
                if (isToday) android.graphics.Color.parseColor("#E3F2FD")
                else android.graphics.Color.WHITE
            )

            binding.cardDay.setOnClickListener {
                if (day.isCurrentMonth) onDayClick(day)
            }
        }
    }
}