package com.biathlonapp.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.biathlonapp.data.model.CalendarDay
import com.biathlonapp.data.model.RaceEvent
import com.biathlonapp.databinding.DialogRaceEventsBinding
import java.text.SimpleDateFormat
import java.util.*

class RaceEventsDialogFragment : BottomSheetDialogFragment() {

    private var _binding: DialogRaceEventsBinding? = null
    private val binding get() = _binding!!
    private lateinit var day: CalendarDay
    private val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
    private lateinit var eventsAdapter: RaceEventsAdapter

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

        setupRecyclerView()
        binding.textDate.text = dateFormat.format(day.date)

        displayEvents(day.events)

        binding.buttonClose.setOnClickListener {
            dismiss()
        }
    }

    private fun setupRecyclerView() {
        eventsAdapter = RaceEventsAdapter { event ->
            // Открыть детали гонки или протокол
            openRaceDetails(event)
        }

        binding.recyclerEvents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventsAdapter
        }
    }

    private fun displayEvents(events: List<RaceEvent>) {
        if (events.isEmpty()) {
            binding.layoutNoEvents.visibility = View.VISIBLE
            binding.recyclerEvents.visibility = View.GONE
        } else {
            binding.layoutNoEvents.visibility = View.GONE
            binding.recyclerEvents.visibility = View.VISIBLE
            eventsAdapter.submitList(events)
        }
    }

    private fun openRaceDetails(event: RaceEvent) {
        // Открыть детали гонки или PDF протокол
        // Можно добавить навигацию к результатам гонки
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