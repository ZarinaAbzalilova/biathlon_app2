package com.biathlonapp.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import com.biathlonapp.R
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.biathlonapp.databinding.FragmentCalendarBinding
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var calendarAdapter: CalendarAdapter
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("MMMM yyyy", Locale("ru"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }
    private lateinit var monthNames: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Загружаем названия месяцев из ресурсов
        monthNames = resources.getStringArray(R.array.month_names)

        setupRecyclerView()
        setupNavigation()
        updateCalendar()
    }

    private fun setupRecyclerView() {
        calendarAdapter = CalendarAdapter { day ->
            if (day.hasEvent) {
                // Открываем страницу с соревнованиями этого дня
                openRaceEventsDialog(day)
            } else {
                // Показываем сообщение, что гонок нет
                showNoEventsMessage()
            }
        }

        binding.recyclerCalendar.apply {
            layoutManager = GridLayoutManager(requireContext(), 7)
            adapter = calendarAdapter
        }

        // Устанавливаем заголовки дней недели
        val daysOfWeek = calendarAdapter.getDayOfWeekHeader()
        binding.textMon.text = daysOfWeek[0]
        binding.textTue.text = daysOfWeek[1]
        binding.textWed.text = daysOfWeek[2]
        binding.textThu.text = daysOfWeek[3]
        binding.textFri.text = daysOfWeek[4]
        binding.textSat.text = daysOfWeek[5]
        binding.textSun.text = daysOfWeek[6]
    }

    private fun setupNavigation() {
        binding.buttonPrevMonth.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateCalendar()
        }

        binding.buttonNextMonth.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateCalendar()
        }
    }

    private fun updateCalendar() {
        // Получаем месяц и год из календаря
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        // Устанавливаем заголовок в именительном падеже
        binding.textMonthYear.text = "${monthNames[month]} $year"

        // Получаем дни для отображения
        val days = getDaysInMonth(calendar)
        calendarAdapter.submitList(days)

        // TODO: Загрузить события для этого месяца с сервера
        loadEventsForMonth(calendar)
    }

    private fun getDaysInMonth(calendar: Calendar): List<com.biathlonapp.data.model.CalendarDay> {
        val days = mutableListOf<com.biathlonapp.data.model.CalendarDay>()

        // Клонируем календарь для расчетов
        val cal = calendar.clone() as Calendar

        // Устанавливаем на первый день месяца
        cal.set(Calendar.DAY_OF_MONTH, 1)

        // Получаем день недели первого дня (1 = воскресенье, 2 = понедельник...)
        var firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK)

        // Корректировка для понедельника как первого дня
        val daysToSubtract = when (firstDayOfWeek) {
            Calendar.SUNDAY -> 6
            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            Calendar.SATURDAY -> 5
            else -> 0
        }

        cal.add(Calendar.DAY_OF_MONTH, -daysToSubtract)

        // Получаем количество дней для отображения (42 дня = 6 недель)
        for (i in 0 until 42) {
            val dayCal = cal.clone() as Calendar
            val isCurrentMonth = cal.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)

            // TODO: Проверить, есть ли события в этот день
            val hasEvent = checkIfDayHasEvent(dayCal)

            days.add(
                com.biathlonapp.data.model.CalendarDay(
                    date = dayCal.time,
                    dayOfMonth = dayCal.get(Calendar.DAY_OF_MONTH),
                    isCurrentMonth = isCurrentMonth,
                    hasEvent = hasEvent
                )
            )

            cal.add(Calendar.DAY_OF_MONTH, 1)
        }

        return days
    }

    private fun checkIfDayHasEvent(calendar: Calendar): Boolean {
        // TODO: Реализовать проверку наличия событий в этот день
        // Пока возвращаем true для некоторых дней для демонстрации
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        return dayOfMonth % 3 == 0 // Для примера: каждый 3-й день
    }

    private fun loadEventsForMonth(calendar: Calendar) {
        // TODO: Загрузить события для этого месяца с сервера
        // После загрузки обновить адаптер
    }

    private fun openRaceEventsDialog(day: com.biathlonapp.data.model.CalendarDay) {
        val dialog = RaceEventsDialogFragment.newInstance(day)
        dialog.show(childFragmentManager, "RaceEventsDialog")
    }

    private fun showNoEventsMessage() {
        android.widget.Toast.makeText(
            requireContext(),
            "В этот день нет гонок",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}