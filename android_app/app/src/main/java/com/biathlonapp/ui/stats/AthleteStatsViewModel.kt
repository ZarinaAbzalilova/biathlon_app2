package com.biathlonapp.ui.stats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.biathlonapp.data.api.BiathlonApiService
import com.biathlonapp.data.repository.BiathlonRepository
import com.biathlonapp.data.repository.FavoritesRepository
import com.biathlonapp.utils.Result
import com.biathlonapp.data.model.AthleteResultsResponse
import com.biathlonapp.utils.DisciplineFormatter
import kotlinx.coroutines.launch

class AthleteStatsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BiathlonRepository()
    private val favoritesRepository = FavoritesRepository(
        application.applicationContext,
        BiathlonApiService.create()
    )

    private val _athleteResults = MutableLiveData<AthleteResultsResponse>()
    val athleteResults: LiveData<AthleteResultsResponse> = _athleteResults

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isFromCache = MutableLiveData(false)
    val isFromCache: LiveData<Boolean> = _isFromCache

    private val _athleteGender = MutableLiveData<String?>()
    val athleteGender: LiveData<String?> = _athleteGender

    // Новые поля для фильтрации
    private val _availableDisciplines = MutableLiveData<List<String>>()
    val availableDisciplines: LiveData<List<String>> = _availableDisciplines

    private val _filteredResults = MutableLiveData<List<RaceResultDisplay>>()
    val filteredResults: LiveData<List<RaceResultDisplay>> = _filteredResults

    private var allResults = listOf<RaceResultDisplay>()
    private var currentDisciplineFilter: String? = null
    private val disciplineMap = mutableMapOf<String, String>()

    fun loadAthleteResults(athleteId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            // ШАГ 1: ВСЕГДА загружаем из кэша сначала
            val cachedResults = favoritesRepository.getCachedResults(athleteId)
            android.util.Log.d("CacheDebug", "Cached results for $athleteId: ${cachedResults.size}")

            if (cachedResults.isNotEmpty()) {
                android.util.Log.d("CacheDebug", "✅ Showing ${cachedResults.size} cached results")
                // Конвертируем кэшированные результаты в RaceResultDisplay
                allResults = cachedResults.map { result ->
                    RaceResultDisplay(
                        discipline = result.discipline ?: "Неизвестно",
                        date = result.date ?: "",
                        nameRace = result.nameRace ?: "",
                        placeRace = result.placeRace ?: "",
                        startNumber = result.startNumber,
                        finishPlace = result.finishPlace,
                        missCount = result.missCount,
                        pdfUrl = null,
                        raceId = null
                    )
                }

                // Обновляем фильтры на основе кэша
                updateDisciplinesFromResults(allResults)
                applyFilter()
                _isFromCache.value = true
                _isLoading.value = false // Снимаем загрузку, так как кэш уже показан
            } else {
                android.util.Log.d("CacheDebug", "⚠️ No cached results for $athleteId")
                // Показываем пустой список, но продолжаем загрузку
                allResults = emptyList()
                updateDisciplinesFromResults(allResults)
                applyFilter()
            }

            // ШАГ 2: Пытаемся загрузить с сервера (если есть интернет)
            when (val result = repository.getAthleteResults(athleteId)) {
                is Result.Success -> {
                    android.util.Log.d("NetworkDebug", "✅ Loaded ${result.data.races?.size} results from network")
                    _athleteGender.value = result.data.athlete.gender
                    _athleteResults.value = result.data

                    // Преобразуем в RaceResultDisplay
                    val networkResults = result.data.races?.map { race ->
                        RaceResultDisplay(
                            discipline = race.raceInfo?.discipline ?: "Неизвестно",
                            date = race.raceInfo?.date ?: "",
                            nameRace = race.raceInfo?.nameRace ?: "",
                            placeRace = race.raceInfo?.placeRace ?: "",
                            startNumber = race.athletePerformance?.startNumber,
                            finishPlace = race.athletePerformance?.finishPlace,
                            missCount = race.athletePerformance?.missCount,
                            pdfUrl = race.pdfUrl,
                            raceId = race.raceId,
                            athleteGender = result.data.athlete.gender
                        )
                    } ?: emptyList()

                    // Обновляем список результатов
                    allResults = networkResults

                    // Обновляем фильтры
                    updateDisciplinesFromResults(allResults)
                    applyFilter()

                    _isLoading.value = false
                    _isFromCache.value = false

                    // Сохраняем в кэш ТОЛЬКО если спортсмен в избранном
                    // Проверяем, находится ли спортсмен в избранном
                    val isFavorite = favoritesRepository.isFavorite(athleteId)
                    if (isFavorite) {
                        result.data.races?.let { races ->
                            viewModelScope.launch {
                                favoritesRepository.saveAthleteResults(athleteId, races)
                                android.util.Log.d("CacheDebug", "💾 Saved ${races.size} results to cache for favorite athlete")
                            }
                        }
                    } else {
                        android.util.Log.d("CacheDebug", "ℹ️ Athlete $athleteId is not favorite, skipping cache save")
                    }
                }
                is Result.Error -> {
                    android.util.Log.e("NetworkDebug", "Network error: ${result.exception.message}")
                    // Если кэш уже показан, не показываем ошибку
                    if (_isFromCache.value != true && allResults.isEmpty()) {
                        _error.value = result.exception.message ?: "Ошибка загрузки"
                        _isLoading.value = false
                    } else if (allResults.isNotEmpty()) {
                        // У нас есть кэш, просто показываем его без ошибки
                        android.util.Log.d("CacheDebug", "Using cached results, ignoring network error")
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    private fun updateDisciplinesFromResults(results: List<RaceResultDisplay>) {
        val uniqueDisciplines = results
            .mapNotNull { it.discipline }
            .filter { it != "Неизвестно" }
            .distinct()
            .sorted()

        disciplineMap.clear()
        val displayDisciplines = mutableListOf("Все дисциплины")
        uniqueDisciplines.forEach { original ->
            val display = DisciplineFormatter.format(original)
            disciplineMap[display] = original
            displayDisciplines.add(display)
        }

        _availableDisciplines.value = displayDisciplines
        android.util.Log.d("FilterDebug", "Updated disciplines: $displayDisciplines")
    }

    fun setDisciplineFilter(displayDiscipline: String?) {
        currentDisciplineFilter = if (displayDiscipline.isNullOrBlank() || displayDiscipline == "Все дисциплины") {
            null
        } else {
            disciplineMap[displayDiscipline]
        }
        applyFilter()
        android.util.Log.d("FilterDebug", "Filter set to: $currentDisciplineFilter, results: ${_filteredResults.value?.size}")
    }
    fun refreshAthleteResults(athleteId: String) {
        viewModelScope.launch {
            // Принудительно загружаем из сети и сохраняем
            when (val result = repository.getAthleteResults(athleteId)) {
                is Result.Success -> {
                    val isFavorite = favoritesRepository.isFavorite(athleteId)
                    if (isFavorite) {
                        result.data.races?.let { races ->
                            favoritesRepository.saveAthleteResults(athleteId, races)
                        }
                    }
                }
                else -> { /* ignore */ }
            }
        }
    }
    private fun applyFilter() {
        val filtered = if (currentDisciplineFilter == null) {
            allResults
        } else {
            allResults.filter { it.discipline == currentDisciplineFilter }
        }
        _filteredResults.value = filtered
        android.util.Log.d("FilterDebug", "Filter applied: ${filtered.size} results")
    }
}