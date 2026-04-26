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
    private lateinit var regularAdapter: RaceProtocolAdapter
    private lateinit var relayAdapter: RelayProtocolAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRaceProtocolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var raceId = intent.getStringExtra(EXTRA_RACE_ID) ?: ""
        val gender = intent.getStringExtra(EXTRA_GENDER)

        // Очищаем race_id от лишних суффиксов
        raceId = raceId.replace("_Смешанная", "")

        val fullRaceId = if (!gender.isNullOrEmpty() && gender != "Смешанная") {
            "$raceId${getGenderSuffix(gender)}"
        } else {
            raceId
        }

        Log.d("RaceProtocol", "Full raceId: $fullRaceId, Gender: $gender")

        setupViewModel()
        setupToolbar()
        setupRecyclerViews()
        setupObservers()

        viewModel.loadRaceResults(fullRaceId, gender)
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

    private fun setupRecyclerViews() {
        regularAdapter = RaceProtocolAdapter { athleteId ->
            val intent = android.content.Intent(this, AthleteDetailActivity::class.java)
            intent.putExtra(AthleteDetailActivity.EXTRA_ATHLETE_ID, athleteId)
            startActivity(intent)
        }

        relayAdapter = RelayProtocolAdapter { teamName, members ->
            // При нажатии на команду можно показать диалог с составом
            showTeamMembersDialog(teamName, members)
        }

        binding.recyclerResults.layoutManager = LinearLayoutManager(this)
    }

    private fun setupObservers() {
        // Наблюдаем за типом гонки
        viewModel.isRelay.observe(this) { isRelay ->
            Log.d("RaceProtocol", "isRelay = $isRelay")
        }

        // Наблюдаем за обычными результатами
        viewModel.raceResults.observe(this) { response ->
            if (response != null) {
                Log.d("RaceProtocol", "Regular results: ${response.results.size} participants")
                displayRaceResults(response)
            }
        }

        // Наблюдаем за эстафетными результатами
        viewModel.relayResults.observe(this) { response ->
            if (response != null) {
                Log.d("RaceProtocol", "Relay results: ${response.results.size} teams")
                displayRelayResults(response)
            }
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

    private fun displayRaceResults(response: com.biathlonapp.data.model.RaceResultsResponse) {
        supportActionBar?.title = response.raceInfo.nameRace
        binding.textRaceInfo.text = buildString {
            if (response.raceInfo.date.isNotEmpty()) {
                append(formatDate(response.raceInfo.date))
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

        regularAdapter.submitList(response.results)
        binding.recyclerResults.adapter = regularAdapter
        binding.textResultsCount.text = "Всего участников: ${response.resultsCount}"
    }

    private fun displayRelayResults(response: com.biathlonapp.data.model.RelayResultsResponse) {
        supportActionBar?.title = response.race_info.name_race
        binding.textRaceInfo.text = buildString {
            if (response.race_info.date.isNotEmpty()) {
                append(formatDate(response.race_info.date))
            }
            if (response.race_info.place_race.isNotEmpty()) {
                if (isNotEmpty()) append(" • ")
                append(response.race_info.place_race)
            }
        }

        relayAdapter.submitList(response.results)
        binding.recyclerResults.adapter = relayAdapter
        binding.textResultsCount.text = "Всего команд: ${response.results_count}"
    }

    private fun showTeamMembersDialog(teamName: String, members: List<com.biathlonapp.data.model.RelayTeamMember>) {
        val membersText = members.joinToString("\n") { member ->
            "${member.leg_number}. ${member.full_name} (${member.miss_count} промахов)"
        }

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(teamName)
            .setMessage(membersText)
            .setPositiveButton("Закрыть", null)
            .show()
    }

    private fun formatDate(dateString: String): String {
        return try {
            val parts = dateString.split("-")
            if (parts.size == 3) {
                val year = parts[0]
                val month = parts[1].padStart(2, '0')
                val day = parts[2].padStart(2, '0')
                "$day.$month.$year"
            } else {
                dateString
            }
        } catch (e: Exception) {
            dateString
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}