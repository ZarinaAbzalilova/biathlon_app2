package com.biathlonapp.ui.athletes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biathlonapp.data.model.Athlete
import com.biathlonapp.data.repository.BiathlonRepository
import com.biathlonapp.utils.Result
import kotlinx.coroutines.launch

class AthletesViewModel : ViewModel() {

    private val repository = BiathlonRepository()

    private val _athletes = MutableLiveData<List<Athlete>>()
    val athletes: LiveData<List<Athlete>> = _athletes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadAthletes()
    }

    fun loadAthletes() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            when (val result = repository.getAthletes()) {  // ← тип выводится автоматически
                is Result.Success -> {
                    _athletes.value = result.data
                    _isLoading.value = false
                }
                is Result.Error -> {
                    _error.value = result.exception.message ?: "Ошибка загрузки спортсменов"
                    _isLoading.value = false
                }
            }
        }
    }

    fun loadAthletesByRank(sportsRank: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result: Result<List<Athlete>> = repository.getAthletes()
            when (result) {
                is Result.Success<List<Athlete>> -> {
                    val filtered = result.data.filter { athlete ->
                        athlete.sportsRank == sportsRank
                    }
                    _athletes.value = filtered
                    _isLoading.value = false
                }
                is Result.Error -> {
                    _error.value = "Ошибка загрузки: ${result.exception.message}"
                    _athletes.value = emptyList()
                    _isLoading.value = false
                }
            }
        }
    }

    fun loadAthletesByGenderAndRank(gender: String, sportsRank: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result: Result<List<Athlete>> = repository.getAthletes()
            when (result) {
                is Result.Success<List<Athlete>> -> {
                    val filtered = result.data.filter { athlete ->
                        athlete.gender.equals(gender, ignoreCase = true) &&
                                athlete.sportsRank == sportsRank
                    }
                    _athletes.value = filtered
                    _isLoading.value = false
                }
                is Result.Error -> {
                    _error.value = "Ошибка загрузки: ${result.exception.message}"
                    _athletes.value = emptyList()
                    _isLoading.value = false
                }
            }
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

            val result: Result<List<Athlete>> = repository.searchAthletes(query)
            when (result) {
                is Result.Success<List<Athlete>> -> {
                    _athletes.value = result.data
                    _isLoading.value = false
                }
                is Result.Error -> {
                    _error.value = result.exception.message ?: "Ошибка поиска"
                    _isLoading.value = false
                }
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}