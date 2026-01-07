package com.biathlonapp.ui.athlete

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.biathlonapp.data.model.Athlete
import com.biathlonapp.databinding.ActivityAthleteDetailBinding
import com.biathlonapp.ui.stats.AthleteStatsActivity
import com.biathlonapp.ui.stats.AthleteStatsViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class AthleteDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ATHLETE_ID = "athlete_id"
        const val EXTRA_ATHLETE = "extra_athlete"
    }

    private lateinit var binding: ActivityAthleteDetailBinding
    private val viewModel: AthleteStatsViewModel by viewModels()
    private var selectedAthlete: Athlete? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAthleteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        selectedAthlete = getAthleteFromIntent()
        selectedAthlete?.let { displayAthleteInfo(it) }

        val athleteId = intent.getStringExtra(EXTRA_ATHLETE_ID) ?: selectedAthlete?.athleteId
        if (athleteId == null) {
            finish()
            return
        }

        loadAthleteData(athleteId)
        setupStatsButton(athleteId)
        observeViewModel()
    }

    private fun getAthleteFromIntent(): Athlete? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_ATHLETE, Athlete::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_ATHLETE)
        }
    }

    private fun loadAthleteData(athleteId: String) {
        viewModel.loadAthleteResults(athleteId)
    }

    private fun setupStatsButton(athleteId: String) {
        binding.buttonStats.setOnClickListener {
            val intent = Intent(this, AthleteStatsActivity::class.java).apply {
                putExtra(AthleteStatsActivity.EXTRA_ATHLETE_ID, athleteId)
            }
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        viewModel.athleteResults.observe(this) { response ->
            displayAthleteInfo(response.athlete)
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                // Handle error - could show error message
                finish()
            }
        }
    }

    private fun displayAthleteInfo(athlete: Athlete) {
        binding.apply {
            editLastName.setText(athlete.lastName ?: "Не указана")
            editFirstName.setText(athlete.firstName ?: "Не указано")
            editBirthDate.setText(formatBirthDate(athlete.birthDate))
            editGender.setText(athlete.displayGender)
            editRegion.setText(athlete.region ?: "Не указан")
            editRegionCode.setText(athlete.regionCode ?: "Не указан")
            editSportsRank.setText(athlete.sportsRank ?: "Не указан")

            // Update toolbar title
            supportActionBar?.title = athlete.fullName
        }
    }

    private fun formatBirthDate(rawDate: String?): String {
        if (rawDate.isNullOrBlank()) return "Не указана"
        return try {
            val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val date = inputFormat.parse(rawDate)
            date?.let { outputFormat.format(it) } ?: rawDate
        } catch (e: Exception) {
            rawDate
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
