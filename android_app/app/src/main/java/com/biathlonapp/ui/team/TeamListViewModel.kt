package com.biathlonapp.ui.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biathlonapp.data.model.Athlete
import com.biathlonapp.data.model.TeamType
import com.biathlonapp.data.repository.TeamRepository
import com.biathlonapp.utils.Result
import kotlinx.coroutines.launch

class TeamListViewModel : ViewModel() {

    private val repository = TeamRepository()

    private val _athletes = MutableLiveData<List<Athlete>>()
    val athletes: LiveData<List<Athlete>> = _athletes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadTeamAthletes(teamType: TeamType) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            when (val result = repository.getTeamAthletes(teamType)) {
                is Result.Success -> {
                    _athletes.value = result.data
                    _isLoading.value = false
                }
                is Result.Error -> {
                    _error.value = result.exception.message ?: "Ошибка загрузки"
                    _isLoading.value = false
                }
            }
        }
    }
}