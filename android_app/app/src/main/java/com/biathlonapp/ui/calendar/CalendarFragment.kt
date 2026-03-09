package com.biathlonapp.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.biathlonapp.data.api.ApiClient
import com.biathlonapp.data.repository.CalendarRepository
import com.biathlonapp.databinding.FragmentCalendarBinding
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var viewModel: CalendarViewModel
    private val calendar = Calendar.getInstance()
    private val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale("ru"))

    private val googleCalendarUrl = "https://calendar.google.com/calendar/embed?src=4b3001e8fde006b0a3ae97e9af0fdeca615609b638ef58f5a0ba8760541c41ba%40group.calendar.google.com&ctz=Asia%2FYekaterinburg"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        setupNavigation()
        setupGoogleCalendarButton()
        setupObservers()

        updateCalendar()
    }

    private fun setupViewModel() {
        // Используем существующий ApiClient
        val apiService = ApiClient.apiService  // ← Вот так правильно!
        val repository = CalendarRepository(apiService)
        viewModel = CalendarViewModel(repository)
    }

    private fun setupRecyclerView() {
        calendarAdapter = CalendarAdapter { day ->
            if (day.hasEvent) {
                viewModel.loadEventsForDay(day)
                openRaceEventsDialog(day)
            } else {
                showNoEventsMessage()
            }
        }

        binding.recyclerCalendar.apply {
            layoutManager = GridLayoutManager(requireContext(), 7)
            adapter = calendarAdapter
        }

        // Устанавливаем заголовки дней недели
        val daysOfWeek = listOf("ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ", "ВС")
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

    private fun setupGoogleCalendarButton() {
        binding.cardGoogleCalendar.setOnClickListener {
            openGoogleCalendar()
        }
    }

    private fun setupObservers() {
        viewModel.calendarDays.observe(viewLifecycleOwner) { days ->
            calendarAdapter.submitList(days)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Показать/скрыть прогресс
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                android.widget.Toast.makeText(requireContext(), it, android.widget.Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.eventsForSelectedDay.observe(viewLifecycleOwner) { events ->
            // Обновить диалог с событиями
        }
    }

    private fun openGoogleCalendar() {
        try {
            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
            intent.data = android.net.Uri.parse(googleCalendarUrl)
            startActivity(intent)
        } catch (e: Exception) {
            try {
                val browserIntent = android.content.Intent(
                    android.content.Intent.ACTION_VIEW,
                    android.net.Uri.parse(googleCalendarUrl)
                )
                startActivity(browserIntent)
            } catch (e: Exception) {
                android.widget.Toast.makeText(
                    requireContext(),
                    "Не удалось открыть календарь",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateCalendar() {
        binding.textMonthYear.text = monthYearFormat.format(calendar.time)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

        // Загружаем события для этого месяца
        viewModel.loadMonthEvents(calendar)
        // Обновляем отображение календаря
        viewModel.updateCalendarDays(calendar)
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

// Фабрика для ViewModel
class CalendarViewModelFactory(
    private val repository: CalendarRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}