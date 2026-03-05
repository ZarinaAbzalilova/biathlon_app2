package com.biathlonapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.biathlonapp.databinding.FragmentAthletesBinding
import com.biathlonapp.ui.athlete.AthleteDetailActivity
import com.biathlonapp.ui.athletes.AthletesAdapter
import com.biathlonapp.ui.athletes.AthletesViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentAthletesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AthletesViewModel by viewModels()
    private lateinit var adapter: AthletesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAthletesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearch()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = AthletesAdapter { athlete ->
            val intent = Intent(requireContext(), AthleteDetailActivity::class.java).apply {
                putExtra(AthleteDetailActivity.EXTRA_ATHLETE_ID, athlete.athleteId)
            }
            startActivity(intent)
        }

        binding.recyclerAthletes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchFragment.adapter
        }
    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString() ?: ""
                if (query.isEmpty()) {
                    viewModel.loadAthletes()
                } else {
                    viewModel.searchAthletes(query)
                }
            }

            override fun afterTextChanged(s: android.text.Editable?) {}
        })
    }

    private fun observeViewModel() {
        viewModel.athletes.observe(viewLifecycleOwner) { athletes ->
            adapter.submitList(athletes)

            if (athletes.isEmpty()) {
                showEmptyState()
            } else {
                hideEmptyState()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressLoading.visibility = View.VISIBLE
                binding.recyclerAthletes.visibility = View.GONE
                binding.layoutError.visibility = View.GONE
                binding.textEmpty.visibility = View.GONE
            } else {
                binding.progressLoading.visibility = View.GONE
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                showError(error)
            } else {
                hideError()
            }
        }
    }

    private fun showError(message: String) {
        binding.layoutError.visibility = View.VISIBLE
        binding.textError.text = message
        binding.buttonRetry.setOnClickListener {
            viewModel.loadAthletes()
        }
        binding.recyclerAthletes.visibility = View.GONE
        binding.textEmpty.visibility = View.GONE
    }

    private fun hideError() {
        binding.layoutError.visibility = View.GONE
        binding.recyclerAthletes.visibility = View.VISIBLE
    }

    private fun showEmptyState() {
        binding.textEmpty.visibility = View.VISIBLE
        binding.recyclerAthletes.visibility = View.GONE
        binding.layoutError.visibility = View.GONE
    }

    private fun hideEmptyState() {
        binding.textEmpty.visibility = View.GONE
        binding.recyclerAthletes.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}