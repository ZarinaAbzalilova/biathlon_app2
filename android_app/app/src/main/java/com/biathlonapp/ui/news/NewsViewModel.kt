package com.biathlonapp.ui.news

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biathlonapp.data.model.News
import com.biathlonapp.data.repository.NewsRepository
import com.biathlonapp.utils.Result
import kotlinx.coroutines.launch

class NewsViewModel(private val context: Context) : ViewModel() {

    private val repository = NewsRepository(context)

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

            val result: Result<List<News>> = repository.getAllNews(forceRefresh = false)  // ← указали тип
            when (result) {
                is Result.Success<List<News>> -> {  // ← указали тип
                    _news.value = result.data
                    _isLoading.value = false
                }
                is Result.Error -> {
                    _error.value = result.exception.message ?: "Ошибка загрузки новостей"
                    _isLoading.value = false
                }
            }
        }
    }

    fun refreshNews() {
        viewModelScope.launch {
            _isLoading.value = true
            val result: Result<List<News>> = repository.getAllNews(forceRefresh = true)  // ← указали тип
            when (result) {
                is Result.Success<List<News>> -> {  // ← указали тип
                    _news.value = result.data
                }
                is Result.Error -> {
                    _error.value = result.exception.message ?: "Ошибка обновления"
                }
            }
            _isLoading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }
}