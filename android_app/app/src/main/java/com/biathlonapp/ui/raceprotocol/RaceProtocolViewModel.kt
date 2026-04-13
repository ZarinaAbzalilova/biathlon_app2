package com.biathlonapp.ui.raceprotocol

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biathlonapp.data.api.ApiClient
import com.biathlonapp.data.model.RaceResultsResponse
import kotlinx.coroutines.launch

class RaceProtocolViewModel : ViewModel() {

    private val apiService = ApiClient.apiService

    private val _raceResults = MutableLiveData<RaceResultsResponse>()
    val raceResults: LiveData<RaceResultsResponse> = _raceResults

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadRaceResults(raceId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                // Временно для теста - полный URL
                val url = "https://biathlon-app2.onrender.com/api/race/$raceId/results"
                android.util.Log.d("RaceProtocol", "Loading: $url")

                val response = apiService.getRaceResults(raceId)
                android.util.Log.d("RaceProtocol", "Response code: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {
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