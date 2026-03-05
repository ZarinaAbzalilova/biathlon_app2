package com.biathlonapp.ui.athletes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biathlonapp.data.model.Athlete
import com.biathlonapp.data.repository.BiathlonRepository
import kotlinx.coroutines.launch

class AthletesViewModel : ViewModel() {

    private val repository = BiathlonRepository()

    // Для главного экрана со списком спортсменов
    private val _athletes = MutableLiveData<List<Athlete>>()
    val athletes: LiveData<List<Athlete>> = _athletes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadAthletes()
    }
    // Для фильтрации по разряду (используем getAthletes())
    fun loadAthletesByRank(sportsRank: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getAthletes().fold(
                onSuccess = { allAthletes ->
                    // Фильтруем по sportsRank
                    val filtered = allAthletes.filter { athlete ->
                        athlete.sportsRank == sportsRank
                    }
                    _athletes.value = filtered
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _error.value = "Ошибка загрузки: ${exception.message}"
                    _athletes.value = emptyList()
                    _isLoading.value = false
                }
            )
        }
    }

    // Для фильтрации по полу и разряду (используем getAthletes())
    fun loadAthletesByGenderAndRank(gender: String, sportsRank: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getAthletes().fold(
                onSuccess = { allAthletes ->
                    val filtered = allAthletes.filter { athlete ->
                        athlete.gender.equals(gender, ignoreCase = true) &&
                                athlete.sportsRank == sportsRank
                    }
                    _athletes.value = filtered
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _error.value = "Ошибка загрузки: ${exception.message}"
                    _athletes.value = emptyList()
                    _isLoading.value = false
                }
            )
        }
    }
    fun loadAthletes() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getAthletes().fold(
                onSuccess = { athletesList ->
                    _athletes.value = athletesList
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _error.value = exception.message ?: "Ошибка загрузки спортсменов"
                    _isLoading.value = false
                }
            )
        }
    }

    fun searchAthletes(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            if (query.length < 2) {
                loadAthletes()
                return@launch
            }

            repository.searchAthletes(query).fold(
                onSuccess = { athletesList ->
                    _athletes.value = athletesList
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _error.value = exception.message ?: "Ошибка поиска"
                    _isLoading.value = false
                }
            )
        }
    }

    fun clearError() {
        _error.value = null
    }
}