package com.biathlonapp.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.biathlonapp.data.api.BiathlonApiService
import com.biathlonapp.data.local.FavoriteAthlete
import com.biathlonapp.data.repository.AuthRepository
import com.biathlonapp.data.repository.FavoritesRepository
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = BiathlonApiService.create()
    private val authRepository = AuthRepository(application.applicationContext)
    private val favoritesRepository = FavoritesRepository(
        application.applicationContext,
        apiService
    )

    private val _favorites = MutableLiveData<List<FavoriteAthlete>>()
    val favorites: LiveData<List<FavoriteAthlete>> = _favorites

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val list = favoritesRepository.getAllFavorites()
                _favorites.value = list
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки: ${e.message}"
                _favorites.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeFromFavorites(athleteId: String) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val token = authRepository.getToken()
                if (token == null) {
                    _error.value = "Авторизуйтесь, чтобы удалять из избранного"
                    _isLoading.value = false
                    return@launch
                }

                // 1. Отправляем запрос на сервер
                android.util.Log.d("Favorites", "Removing from server: athleteId=$athleteId")
                val response = apiService.removeFavorite("Bearer $token", athleteId.toLong())
                android.util.Log.d("Favorites", "Remove response code: ${response.code()}")

                if (response.isSuccessful) {
                    // 2. Если сервер подтвердил, удаляем локально
                    val success = favoritesRepository.removeFromFavorites(athleteId)
                    android.util.Log.d("Favorites", "Local remove result: $success")

                    if (success) {
                        // 3. Обновляем список
                        loadFavorites()
                        _error.value = null
                    } else {
                        _error.value = "Не удалось удалить локально"
                    }
                } else {
                    _error.value = "Ошибка при удалении с сервера: ${response.code()}"
                }
            } catch (e: Exception) {
                android.util.Log.e("Favorites", "Error removing from favorites", e)
                _error.value = "Ошибка: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}