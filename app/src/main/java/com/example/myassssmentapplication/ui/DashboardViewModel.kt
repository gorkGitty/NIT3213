package com.example.myassssmentapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassssmentapplication.repository.ArtworkRepository
import kotlinx.coroutines.launch

sealed class DashboardResult {
    data class Success(val entities: List<Map<String, Any>>) : DashboardResult()
    data class Error(val message: String) : DashboardResult()
    object Loading : DashboardResult()
}

class DashboardViewModel(private val repository: ArtworkRepository) : ViewModel() {
    private val _dashboardResult = MutableLiveData<DashboardResult>()
    val dashboardResult: LiveData<DashboardResult> = _dashboardResult

    fun loadArtworks(keypass: String) {
        viewModelScope.launch {
            _dashboardResult.value = DashboardResult.Loading
            try {
                val entities = repository.getArtworks(keypass)
                _dashboardResult.value = DashboardResult.Success(entities)
            } catch (e: Exception) {
                _dashboardResult.value = DashboardResult.Error(e.message ?: "An error occurred")
            }
        }
    }
} 