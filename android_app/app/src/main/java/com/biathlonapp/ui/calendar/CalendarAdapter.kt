package com.biathlonapp.ui.calendar

import android.view.LayoutInflater
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

            // Для дней не из текущего месяца делаем текст бледнее
            if (!day.isCurrentMonth) {
                binding.textDay.alpha = 0.3f
            } else {
                binding.textDay.alpha = 1.0f
            }

            // Выделяем дни с гонками
            if (day.hasEvent) {
                binding.viewEventDot.visibility = android.view.View.VISIBLE
                binding.textDay.setTextColor(android.graphics.Color.parseColor("#2196F3"))
            } else {
                binding.viewEventDot.visibility = android.view.View.GONE
                binding.textDay.setTextColor(android.graphics.Color.BLACK)
            }

            // Проверяем, является ли день сегодняшним
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

            if (isToday) {
                binding.cardDay.setCardBackgroundColor(android.graphics.Color.parseColor("#E3F2FD"))
            } else {
                binding.cardDay.setCardBackgroundColor(android.graphics.Color.WHITE)
            }

            binding.cardDay.setOnClickListener {
                if (day.isCurrentMonth) {
                    onDayClick(day)
                }
            }
        }
    }
}