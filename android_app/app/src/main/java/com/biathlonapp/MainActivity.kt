package com.biathlonapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.biathlonapp.databinding.ActivityMainBinding
import com.biathlonapp.ui.athletes.AthletesAdapter
import com.biathlonapp.ui.athletes.AthletesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: AthletesViewModel by viewModels()
    private lateinit var adapter: AthletesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearch()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = AthletesAdapter { athlete ->
            // Handle athlete click - navigate to detail
            val intent = Intent(this, com.biathlonapp.ui.athlete.AthleteDetailActivity::class.java).apply {
                putExtra(com.biathlonapp.ui.athlete.AthleteDetailActivity.EXTRA_ATHLETE_ID, athlete.athleteId)
            }
            startActivity(intent)
        }

        binding.recyclerAthletes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupSearch() {
        // Используем addTextChangedListener для EditText
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
        viewModel.athletes.observe(this) { athletes ->
            adapter.submitList(athletes)

            if (athletes.isEmpty()) {
                showEmptyState()
            } else {
                hideEmptyState()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressLoading.visibility = View.VISIBLE
                binding.recyclerAthletes.visibility = View.GONE
                binding.layoutError.visibility = View.GONE
                binding.textEmpty.visibility = View.GONE
            } else {
                binding.progressLoading.visibility = View.GONE
            }
        }

        viewModel.error.observe(this) { error ->
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
}