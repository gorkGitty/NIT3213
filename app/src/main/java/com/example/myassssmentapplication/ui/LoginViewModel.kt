package com.example.myassssmentapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassssmentapplication.repository.ArtworkRepository
import kotlinx.coroutines.launch

sealed class LoginResult {
    data class Success(val keypass: String) : LoginResult()
    data class Error(val message: String) : LoginResult()
    object Loading : LoginResult()
}

class LoginViewModel(private val repository: ArtworkRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = LoginResult.Loading
            try {
                val response = repository.login(username, password)
                if (response?.keypass != null) {
                    _loginResult.value = LoginResult.Success(response.keypass)
                } else {
                    _loginResult.value = LoginResult.Error("Invalid credentials")
                }
            } catch (e: Exception) {
                _loginResult.value = LoginResult.Error(e.message ?: "An error occurred")
            }
        }
    }
} 