package com.biathlonapp.ui.raceprotocol

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biathlonapp.data.api.ApiClient
import com.biathlonapp.data.model.RaceResultsResponse
import com.biathlonapp.data.model.RelayResultsResponse
import kotlinx.coroutines.launch

class RaceProtocolViewModel : ViewModel() {

    private val apiService = ApiClient.apiService

    // Для обычных гонок
    private val _raceResults = MutableLiveData<RaceResultsResponse>()
    val raceResults: LiveData<RaceResultsResponse> = _raceResults

    // Для эстафет
    private val _relayResults = MutableLiveData<RelayResultsResponse>()
    val relayResults: LiveData<RelayResultsResponse> = _relayResults

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isRelay = MutableLiveData(false)
    val isRelay: LiveData<Boolean> = _isRelay

    fun loadRaceResults(raceId: String, gender: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                // Просто загружаем relay-results, бэкенд сам отфильтрует по полу из raceId
                val relayResponse = apiService.getRelayResults(raceId)

                if (relayResponse.isSuccessful && relayResponse.body() != null) {
                    val body = relayResponse.body()!!
                    if (body.results.isNotEmpty()) {
                        _isRelay.value = true
                        _relayResults.value = body
                        _isLoading.value = false
                        return@launch
                    }
                }

                // Если не эстафета - загружаем обычные результаты
                val response = apiService.getRaceResults(raceId, gender)
                android.util.Log.d("RaceProtocol", "Regular response code: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {
                    _isRelay.value = false
                    _raceResults.value = response.body()
                } else {
                    _error.value = "Ошибка загрузки: ${response.code()}"
                }
            } catch (e: Exception) {
                android.util.Log.e("RaceProtocol", "Error: ${e.message}")
                _error.value = e.message ?: "Ошибка подключения"
            }

            _isLoading.value = false
        }
    }
}