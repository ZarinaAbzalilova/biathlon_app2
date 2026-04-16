package com.biathlonapp.ui.athlete

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.biathlonapp.R
import com.biathlonapp.data.api.ApiClient
import com.biathlonapp.data.api.BiathlonApiService
import com.biathlonapp.data.model.Athlete
import com.biathlonapp.data.repository.AuthRepository
import com.biathlonapp.data.repository.FavoritesRepository
import com.biathlonapp.databinding.ActivityAthleteDetailBinding
import com.biathlonapp.ui.stats.AthleteStatsActivity
import com.biathlonapp.ui.stats.AthleteStatsViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class AthleteDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ATHLETE_ID = "athlete_id"
        const val EXTRA_ATHLETE = "extra_athlete"
    }

    private lateinit var binding: ActivityAthleteDetailBinding
    private val viewModel: AthleteStatsViewModel by viewModels()
    private lateinit var favoritesRepository: FavoritesRepository
    private lateinit var authRepository: AuthRepository
    private lateinit var apiService: BiathlonApiService
    private var selectedAthlete: Athlete? = null
    private var athleteId: String? = null
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAthleteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализируем все зависимости
        apiService = ApiClient.apiService
        authRepository = AuthRepository(this)  // ← ВАЖНО!
        favoritesRepository = FavoritesRepository(this, apiService)

        setupToolbar()

        selectedAthlete = getAthleteFromIntent()
        athleteId = intent.getStringExtra(EXTRA_ATHLETE_ID) ?: selectedAthlete?.athleteId

        val currentId = athleteId

        if (currentId == null) {
            Toast.makeText(this, "Ошибка: спортсмен не найден", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupStatsButton(currentId, selectedAthlete?.gender)
        loadFromCache(currentId)
        selectedAthlete?.let { displayAthleteInfo(it) }
        loadAthleteData(currentId)
        setupFavoriteButton()
        checkIfFavorite(currentId)
        observeViewModel()
    }

    private fun loadFromCache(athleteId: String) {
        lifecycleScope.launch {
            val cachedAthlete = favoritesRepository.getFavoriteAthleteById(athleteId)
            if (cachedAthlete != null) {
                val athlete = Athlete(
                    athleteId = cachedAthlete.athleteId,
                    lastName = cachedAthlete.lastName ?: cachedAthlete.surname,
                    firstName = cachedAthlete.firstName ?: cachedAthlete.name,
                    gender = cachedAthlete.gender,
                    region = cachedAthlete.region,
                    regionCode = cachedAthlete.regionCode,
                    sportsRank = cachedAthlete.sportsRank,
                    birthDate = cachedAthlete.birthDate
                )
                displayAthleteInfo(athlete)
                supportActionBar?.title = "${cachedAthlete.surname} ${cachedAthlete.name}".trim()
            }
        }
    }

    private fun setupFavoriteButton() {
        binding.buttonFavorite.setOnClickListener {
            toggleFavorite()
        }
        updateFavoriteButton()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Загрузка..."
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

    private fun setupStatsButton(athleteId: String, athleteGender: String?) {
        binding.buttonStats.setOnClickListener {
            if (athleteId.isNotEmpty()) {
                val intent = Intent(this, AthleteStatsActivity::class.java).apply {
                    putExtra(AthleteStatsActivity.EXTRA_ATHLETE_ID, athleteId)
                    putExtra(AthleteStatsActivity.EXTRA_ATHLETE_GENDER, athleteGender)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Ошибка: ID спортсмена не найден", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkIfFavorite(athleteId: String) {
        lifecycleScope.launch {
            isFavorite = favoritesRepository.isFavorite(athleteId)
            updateFavoriteButton()
        }
    }

    private fun toggleFavorite() {
        lifecycleScope.launch {
            val currentId = athleteId ?: return@launch
            val token = authRepository.getToken()

            if (token == null) {
                showMessage("Авторизуйтесь, чтобы добавлять в избранное")
                return@launch
            }

            if (isFavorite) {
                // Удаляем с сервера
                try {
                    val response = apiService.removeFavorite("Bearer $token", currentId.toLong())
                    if (response.isSuccessful) {
                        favoritesRepository.removeFromFavorites(currentId)
                        showMessage("Удалено из избранного")
                        isFavorite = false
                    } else {
                        showMessage("Ошибка при удалении")
                    }
                } catch (e: Exception) {
                    showMessage("Ошибка: ${e.message}")
                }
            } else {
                // Добавляем на сервер
                try {
                    val response = apiService.addFavorite("Bearer $token", mapOf("athlete_id" to currentId.toLong()))
                    if (response.isSuccessful) {
                        val athlete = selectedAthlete ?: createMinimalAthlete(currentId)
                        favoritesRepository.addToFavorites(athlete)
                        showMessage("Добавлено в избранное")
                        isFavorite = true
                        viewModel.loadAthleteResults(currentId)
                    } else {
                        showMessage("Ошибка при добавлении")
                    }
                } catch (e: Exception) {
                    showMessage("Ошибка: ${e.message}")
                }
            }
            updateFavoriteButton()
        }
    }

    private fun createMinimalAthlete(id: String): Athlete {
        return Athlete(
            athleteId = id,
            lastName = binding.editLastName.text.toString().takeIf { it.isNotBlank() } ?: selectedAthlete?.lastName ?: "",
            firstName = binding.editFirstName.text.toString().takeIf { it.isNotBlank() } ?: selectedAthlete?.firstName ?: "",
            gender = binding.editGender.text.toString().takeIf { it.isNotBlank() } ?: selectedAthlete?.gender ?: "",
            region = binding.editRegion.text.toString().takeIf { it.isNotBlank() } ?: selectedAthlete?.region,
            regionCode = binding.editRegionCode.text.toString().takeIf { it.isNotBlank() } ?: selectedAthlete?.regionCode,
            sportsRank = binding.editSportsRank.text.toString().takeIf { it.isNotBlank() } ?: selectedAthlete?.sportsRank ?: "",
            birthDate = binding.editBirthDate.text.toString().takeIf { it.isNotBlank() } ?: selectedAthlete?.birthDate
        )
    }

    private fun updateFavoriteButton() {
        if (isFavorite) {
            binding.buttonFavorite.text = "В избранном"
            val drawable = ContextCompat.getDrawable(this, R.drawable.ic_star_filled)
            binding.buttonFavorite.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
            binding.buttonFavorite.backgroundTintList = android.content.res.ColorStateList.valueOf(
                android.graphics.Color.parseColor("#4CAF50")
            )
        } else {
            binding.buttonFavorite.text = "В избранное"
            val drawable = ContextCompat.getDrawable(this, R.drawable.ic_star_border)
            binding.buttonFavorite.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
            binding.buttonFavorite.backgroundTintList = android.content.res.ColorStateList.valueOf(
                android.graphics.Color.parseColor("#FFC107")
            )
        }
    }

    private fun observeViewModel() {
        viewModel.athleteResults.observe(this) { response ->
            selectedAthlete = response.athlete
            displayAthleteInfo(response.athlete)

            if (isFavorite) {
                updateFavoriteAthleteData(response.athlete)
            }
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, "Ошибка загрузки: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateFavoriteAthleteData(athlete: Athlete) {
        lifecycleScope.launch {
            favoritesRepository.updateFavoriteAthlete(athlete)
        }
    }

    private fun displayAthleteInfo(athlete: Athlete) {
        binding.apply {
            editLastName.setText(athlete.lastName ?: "Не указана")
            editFirstName.setText(athlete.firstName ?: "Не указано")
            editBirthDate.setText(formatBirthDate(athlete.birthDate))
            editGender.setText(formatGender(athlete.gender))
            editRegion.setText(athlete.region ?: "Не указан")
            editRegionCode.setText(athlete.regionCode ?: "Не указан")
            editSportsRank.setText(athlete.sportsRank ?: "Не указан")

            val fullName = "${athlete.lastName ?: ""} ${athlete.firstName ?: ""}".trim()
            supportActionBar?.title = if (fullName.isNotBlank()) fullName else "Спортсмен"
        }
    }

    private fun formatGender(gender: String?): String {
        return when (gender?.lowercase()) {
            "male", "м", "мужской" -> "Мужской"
            "female", "ж", "женский" -> "Женский"
            else -> "Не указан"
        }
    }

    private fun formatBirthDate(rawDate: String?): String {
        if (rawDate.isNullOrBlank()) return "Не указана"
        return try {
            val formats = listOf(
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()),
                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()),
                SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
            )

            for (format in formats) {
                try {
                    val date = format.parse(rawDate)
                    if (date != null) {
                        val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                        return outputFormat.format(date)
                    }
                } catch (e: Exception) {
                    // Пробуем следующий формат
                }
            }
            rawDate
        } catch (e: Exception) {
            rawDate
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}