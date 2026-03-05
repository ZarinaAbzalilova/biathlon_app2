package com.biathlonapp.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biathlonapp.data.model.News
import com.biathlonapp.data.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val repository = NewsRepository()

    private val _news = MutableLiveData<List<News>>()
    val news: LiveData<List<News>> = _news

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadNews() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val result = repository.getAllNews()
                result.fold(
                    onSuccess = { newsList ->
                        _news.value = newsList
                        _isLoading.value = false
                    },
                    onFailure = { exception ->
                        _error.value = "Ошибка загрузки новостей: ${exception.message}"
                        _isLoading.value = false
                    }
                )
            } catch (e: Exception) {
                _error.value = "Ошибка: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun refresh() {
        loadNews()
    }

    fun clearError() {
        _error.value = null
    }
}