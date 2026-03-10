package com.biathlonapp.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.biathlonapp.data.local.FavoriteAthlete
import com.biathlonapp.data.repository.FavoritesRepository
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FavoritesRepository(application.applicationContext)

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
                val list = repository.getAllFavorites()
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
            val success = repository.removeFromFavorites(athleteId)
            if (success) {
                loadFavorites() // Перезагружаем список
            } else {
                _error.value = "Не удалось удалить из избранного"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}