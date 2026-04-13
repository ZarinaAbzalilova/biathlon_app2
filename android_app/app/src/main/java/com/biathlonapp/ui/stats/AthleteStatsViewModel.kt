package com.biathlonapp.ui.stats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.biathlonapp.data.repository.BiathlonRepository
import com.biathlonapp.data.repository.FavoritesRepository
import com.biathlonapp.utils.Result
import com.biathlonapp.data.model.AthleteResultsResponse
import com.biathlonapp.utils.DisciplineFormatter
import kotlinx.coroutines.launch

class AthleteStatsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BiathlonRepository()
    private val favoritesRepository = FavoritesRepository(application.applicationContext)

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
            _isFromCache.value = false

            // Проверяем кэш
            try {
                val cachedResults = favoritesRepository.getCachedResults(athleteId)
                if (cachedResults.isNotEmpty()) {
                    // TODO: Конвертировать cachedResults в AthleteResultsResponse если нужно
                    _isFromCache.value = true
                }
            } catch (e: Exception) {
                // Игнорируем ошибки кэша
            }

            // Загружаем с сервера
            when (val result = repository.getAthleteResults(athleteId)) {
                is Result.Success -> {
                    _athleteGender.value = result.data.athlete.gender
                    _athleteResults.value = result.data

                    // Преобразуем в RaceResultDisplay для отображения
                    allResults = result.data.races?.map { race ->
                        RaceResultDisplay(
                            discipline = race.raceInfo?.discipline ?: "Неизвестно",
                            date = race.raceInfo?.date ?: "",
                            nameRace = race.raceInfo?.nameRace ?: "",
                            placeRace = race.raceInfo?.placeRace ?: "",
                            startNumber = race.athletePerformance?.startNumber,
                            finishPlace = race.athletePerformance?.finishPlace,
                            missCount = race.athletePerformance?.missCount,
                            pdfUrl = race.pdfUrl,
                            raceId = race.raceId
                        )
                    } ?: emptyList()

                    // Извлекаем уникальные дисциплины
                    val uniqueDisciplines = allResults
                        .mapNotNull { it.discipline }
                        .filter { it != "Неизвестно" }  // ← Исключаем "Неизвестно"
                        .distinct()
                        .sorted()

                    // Создаем маппинг и список для отображения
                    disciplineMap.clear()
                    val displayDisciplines = mutableListOf("Все дисциплины")
                    uniqueDisciplines.forEach { original ->
                        val display = DisciplineFormatter.format(original)
                        disciplineMap[display] = original
                        displayDisciplines.add(display)
                    }

                    _availableDisciplines.value = displayDisciplines

                    // Применяем текущий фильтр (если есть)
                    applyFilter()

                    _isLoading.value = false

                    // Сохраняем в кэш
                    result.data.races?.let { races ->
                        viewModelScope.launch {
                            favoritesRepository.saveAthleteResults(athleteId, races)
                        }
                    }
                }
                is Result.Error -> {
                    _error.value = result.exception.message ?: "Ошибка загрузки"
                    _isLoading.value = false
                }
            }
        }
    }

    fun setDisciplineFilter(displayDiscipline: String?) {
        currentDisciplineFilter = if (displayDiscipline.isNullOrBlank() || displayDiscipline == "Все дисциплины") {
            null
        } else {
            disciplineMap[displayDiscipline]  // ← получаем оригинальное название
        }
        applyFilter()
        // Добавим логирование для отладки
        android.util.Log.d("FilterDebug", "Selected display: $displayDiscipline, original: $currentDisciplineFilter")
    }

    private fun applyFilter() {
        val filtered = if (currentDisciplineFilter == null) {
            allResults
        } else {
            allResults.filter { it.discipline == currentDisciplineFilter }
        }
        _filteredResults.value = filtered
        android.util.Log.d("FilterDebug", "Filter applied: ${currentDisciplineFilter}, results count: ${filtered.size}")
    }
}