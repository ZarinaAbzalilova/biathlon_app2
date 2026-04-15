package com.biathlonapp.ui.raceprotocol

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.biathlonapp.databinding.ActivityRaceProtocolBinding
import com.biathlonapp.ui.athlete.AthleteDetailActivity

class RaceProtocolActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_RACE_ID = "race_id"
        const val EXTRA_GENDER = "gender"
    }

    private lateinit var binding: ActivityRaceProtocolBinding
    private lateinit var viewModel: RaceProtocolViewModel
    private lateinit var adapter: RaceProtocolAdapter
    private var raceId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRaceProtocolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val raceId = intent.getStringExtra(EXTRA_RACE_ID) ?: ""
        val gender = intent.getStringExtra(EXTRA_GENDER)
        val fullRaceId = if (!gender.isNullOrEmpty()) {
            "$raceId${getGenderSuffix(gender)}"
        } else {
            raceId
        }

        Log.d("RaceProtocol", "Original raceId: $raceId, Gender: $gender, Full: $fullRaceId")

        setupViewModel()
        setupToolbar()
        setupRecyclerView()
        setupObservers()

        // Передаем полный raceId с суффиксом
        viewModel.loadRaceResults(fullRaceId)
    }
    private fun getGenderSuffix(gender: String): String {
        return when (gender) {
            "М", "мужской", "male", "Мужчины" -> "_М"
            "Ж", "женский", "female", "Женщины" -> "_Ж"
            else -> ""
        }
    }
    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[RaceProtocolViewModel::class.java]
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        adapter = RaceProtocolAdapter { athleteId ->
            val intent = android.content.Intent(this, AthleteDetailActivity::class.java)
            intent.putExtra(AthleteDetailActivity.EXTRA_ATHLETE_ID, athleteId)
            startActivity(intent)
        }

        binding.recyclerResults.apply {
            layoutManager = LinearLayoutManager(this@RaceProtocolActivity)
            adapter = this@RaceProtocolActivity.adapter
        }
    }

    private fun setupObservers() {
        viewModel.raceResults.observe(this) { response ->
            supportActionBar?.title = response.raceInfo.nameRace
            binding.textRaceInfo.text = buildString {
                append(response.raceInfo.discipline ?: "")
                if (response.raceInfo.date.isNotEmpty()) {
                    if (isNotEmpty()) append(" • ")
                    append(response.raceInfo.date)
                }
                if (response.raceInfo.placeRace.isNotEmpty()) {
                    if (isNotEmpty()) append(" • ")
                    append(response.raceInfo.placeRace)
                }
                if (response.raceInfo.gender != null) {
                    if (isNotEmpty()) append(" • ")
                    append(when (response.raceInfo.gender) {
                        "М" -> "Мужчины"
                        "Ж" -> "Женщины"
                        else -> response.raceInfo.gender
                    })
                }
            }

            adapter.submitList(response.results)

            binding.textResultsCount.text = "Всего участников: ${response.resultsCount}"
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { error ->
            if (error != null) {
                binding.layoutError.visibility = View.VISIBLE
                binding.textError.text = error
                binding.recyclerResults.visibility = View.GONE
            } else {
                binding.layoutError.visibility = View.GONE
                binding.recyclerResults.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}