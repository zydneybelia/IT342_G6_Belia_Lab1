package com.example.quickbite_mobile.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbite_mobile.data.model.LoginRequest
import com.example.quickbite_mobile.data.model.RegisterRequest
import com.example.quickbite_mobile.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val sessionManager = SessionManager(application)
    
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        viewModelScope.launch {
            sessionManager.authToken.collect { token ->
                RetrofitClient.setToken(token)
                if (token != null) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Unauthenticated
                }
            }
        }
    }

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = RetrofitClient.instance.login(request)
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!
                    sessionManager.saveAuthData(
                        token = loginResponse.token,
                        email = loginResponse.email,
                        name = "${loginResponse.firstname} ${loginResponse.lastname}"
                    )
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error("Login failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Network error: ${e.message}")
            }
        }
    }

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = RetrofitClient.instance.register(request)
                if (response.isSuccessful) {
                    _authState.value = AuthState.RegistrationSuccess
                } else {
                    _authState.value = AuthState.Error("Registration failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Network error: ${e.message}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
            RetrofitClient.setToken(null)
            _authState.value = AuthState.Unauthenticated
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object RegistrationSuccess : AuthState()
    data class Error(val message: String) : AuthState()
}
