package com.biathlonapp.ui.stats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.biathlonapp.data.model.AthleteResultsResponse
import com.biathlonapp.data.repository.BiathlonRepository
import com.biathlonapp.data.repository.FavoritesRepository
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
            repository.getAthleteResults(athleteId).fold(
                onSuccess = { response ->
                    _athleteResults.value = response
                    _isLoading.value = false

                    // Сохраняем в кэш
                    viewModelScope.launch {
                        favoritesRepository.saveAthleteResults(athleteId, response.races)
                    }
                },
                onFailure = { exception ->
                    _error.value = exception.message ?: "Ошибка загрузки"
                    _isLoading.value = false
                }
            )
        }
    }
}