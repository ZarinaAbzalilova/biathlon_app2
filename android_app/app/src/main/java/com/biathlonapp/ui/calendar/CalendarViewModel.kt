package com.biathlonapp.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biathlonapp.data.model.CalendarDay
import com.biathlonapp.data.model.RaceEvent
import com.biathlonapp.data.repository.CalendarRepository
import com.biathlonapp.utils.Result
import kotlinx.coroutines.launch
import java.util.*

class CalendarViewModel(
    private val repository: CalendarRepository
) : ViewModel() {

    private val _calendarDays = MutableLiveData<List<CalendarDay>>()
    val calendarDays: LiveData<List<CalendarDay>> = _calendarDays

    private val _eventsForSelectedDay = MutableLiveData<List<RaceEvent>>()
    val eventsForSelectedDay: LiveData<List<RaceEvent>> = _eventsForSelectedDay

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private var currentMonthEvents = listOf<RaceEvent>()

    fun loadMonthEvents(calendar: Calendar) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)

            val result = repository.getRacesForMonth(year, month)

            when (result) {
                is Result.Success -> {
                    currentMonthEvents = result.data
                    _isLoading.value = false
                }
                is Result.Error -> {
                    _error.value = result.exception.message ?: "Ошибка загрузки календаря"
                    _isLoading.value = false
                }
            }
        }
    }

    fun updateCalendarDays(calendar: Calendar) {
        val days = generateCalendarDays(calendar, currentMonthEvents)
        _calendarDays.value = days
    }

    fun loadEventsForDay(day: CalendarDay) {
        val eventsForDay = currentMonthEvents.filter { event ->
            val eventCal = Calendar.getInstance()
            eventCal.time = event.date
            val dayCal = Calendar.getInstance()
            dayCal.time = day.date

            eventCal.get(Calendar.YEAR) == dayCal.get(Calendar.YEAR) &&
                    eventCal.get(Calendar.MONTH) == dayCal.get(Calendar.MONTH) &&
                    eventCal.get(Calendar.DAY_OF_MONTH) == dayCal.get(Calendar.DAY_OF_MONTH)
        }
        _eventsForSelectedDay.value = eventsForDay
    }

    private fun generateCalendarDays(
        calendar: Calendar,
        events: List<RaceEvent>
    ): List<CalendarDay> {
        val days = mutableListOf<CalendarDay>()
        val cal = calendar.clone() as Calendar

        // Устанавливаем на первый день месяца
        cal.set(Calendar.DAY_OF_MONTH, 1)

        // Получаем день недели первого дня
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

        // Группируем события по датам для быстрого доступа
        val eventsByDate = events.groupBy { event ->
            val eventCal = Calendar.getInstance()
            eventCal.time = event.date
            Triple(
                eventCal.get(Calendar.YEAR),
                eventCal.get(Calendar.MONTH),
                eventCal.get(Calendar.DAY_OF_MONTH)
            )
        }

        // Генерируем 42 дня (6 недель)
        for (i in 0 until 42) {
            val dayCal = cal.clone() as Calendar
            val isCurrentMonth = cal.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)

            val dateKey = Triple(
                dayCal.get(Calendar.YEAR),
                dayCal.get(Calendar.MONTH),
                dayCal.get(Calendar.DAY_OF_MONTH)
            )

            val dayEvents = eventsByDate[dateKey] ?: emptyList()

            days.add(
                CalendarDay(
                    date = dayCal.time,
                    dayOfMonth = dayCal.get(Calendar.DAY_OF_MONTH),
                    isCurrentMonth = isCurrentMonth,
                    hasEvent = dayEvents.isNotEmpty(),
                    events = dayEvents
                )
            )

            cal.add(Calendar.DAY_OF_MONTH, 1)
        }

        return days
    }
}