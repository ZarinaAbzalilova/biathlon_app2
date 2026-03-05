package com.biathlonapp.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.biathlonapp.data.model.CalendarDay
import com.biathlonapp.databinding.DialogRaceEventsBinding
import java.text.SimpleDateFormat
import java.util.*

class RaceEventsDialogFragment : BottomSheetDialogFragment() {

    private var _binding: DialogRaceEventsBinding? = null
    private val binding get() = _binding!!
    private lateinit var day: CalendarDay
    private val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        day = arguments?.getSerializable("day") as CalendarDay
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogRaceEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textDate.text = dateFormat.format(day.date)

        // TODO: Загрузить события для этого дня с сервера
        loadEventsForDay()

        binding.buttonClose.setOnClickListener {
            dismiss()
        }
    }

    private fun loadEventsForDay() {
        // TODO: Загрузить события с сервера
        // Пока показываем заглушку
        if (day.hasEvent) {
            binding.layoutNoEvents.visibility = View.GONE
            binding.recyclerEvents.visibility = View.VISIBLE
            // TODO: Создать адаптер для списка событий
        } else {
            binding.layoutNoEvents.visibility = View.VISIBLE
            binding.recyclerEvents.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance(day: CalendarDay): RaceEventsDialogFragment {
            val fragment = RaceEventsDialogFragment()
            val args = Bundle()
            args.putSerializable("day", day)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}