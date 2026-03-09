package com.biathlonapp.ui.stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biathlonapp.data.model.AthleteResultsResponse
import com.biathlonapp.data.model.RaceResult
import com.biathlonapp.data.repository.BiathlonRepository
import kotlinx.coroutines.launch

class AthleteStatsViewModel : ViewModel() {

    private val repository = BiathlonRepository()

    private val _athleteResults = MutableLiveData<AthleteResultsResponse>()
    val athleteResults: LiveData<AthleteResultsResponse> = _athleteResults

    private val _filteredResults = MutableLiveData<List<RaceResult>>()
    val filteredResults: LiveData<List<RaceResult>> = _filteredResults

    private val _availableDisciplines = MutableLiveData<List<String>>()
    val availableDisciplines: LiveData<List<String>> = _availableDisciplines

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private var disciplineFilter: String? = null
    private var allResults: List<RaceResult> = emptyList()

    fun setDisciplineFilter(filter: String?) {
        disciplineFilter = filter?.takeIf { it.isNotBlank() && it != "Все дисциплины" }
        applyFilter()
    }

    fun getDisciplineFilter(): String? = disciplineFilter

    fun loadAthleteResults(athleteId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getAthleteResults(athleteId).fold(
                onSuccess = { results ->
                    _athleteResults.value = results
                    allResults = results.results

                    // Extract unique disciplines
                    val disciplines = results.results
                        .mapNotNull { it.raceInfo?.discipline }
                        .distinct()
                        .sorted()

                    _availableDisciplines.value = listOf("Все дисциплины") + disciplines
                    applyFilter()
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _error.value = exception.message ?: "Ошибка загрузки статистики"
                    _isLoading.value = false
                }
            )
        }
    }

    private fun applyFilter() {
        val filtered = if (disciplineFilter.isNullOrBlank()) {
            allResults
        } else {
            allResults.filter {
                it.raceInfo?.discipline.equals(disciplineFilter, ignoreCase = true)
            }
        }
        _filteredResults.value = filtered
    }

    fun clearError() {
        _error.value = null
    }

    // В AthleteStatsViewModel
    fun getPdfUrl(raceId: String?, athleteId: String, callback: (String?) -> Unit) {
        if (raceId == null) {
            callback(null)
            return
        }

        viewModelScope.launch {
            try {
                val pdfUrl = repository.getRacePdfUrl(raceId, athleteId)
                callback(pdfUrl)
            } catch (e: Exception) {
                callback(null)
            }
        }
    }

}