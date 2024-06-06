package com.example.jetpackcomposetodoapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposetodoapp.data.AuthRepository
import com.example.jetpackcomposetodoapp.di.Register
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    @Register private val authRepository: AuthRepository,
    private val providePasswordRegex: Regex
) : ViewModel() {

    private val _isRegistrationSuccessful = MutableLiveData<Boolean>()
    val isRegistrationSuccessful: LiveData<Boolean> get() = _isRegistrationSuccessful

    private val _registerToLogin = MutableLiveData<Boolean>()
    val registerToLogin: LiveData<Boolean> get() = _registerToLogin

    private val _toastMessage = MutableLiveData<CharSequence>()
    val toastMessage: LiveData<CharSequence> get() = _toastMessage

    fun registerUser(email: String, password: String) {
        if (!providePasswordRegex.matches(password)) {
            Log.d("RegisterViewModel", "Password Regex NOT matches")
            return
        }

        viewModelScope.launch {
            try {
                Log.d("RegisterViewModel", "Starting authentication")
                val result = authRepository.authenticate(email, password)
                Log.d("RegisterViewModel", "Authentication result received: $result")

                if (result.isSuccess) {
                    _isRegistrationSuccessful.value = true
                    _toastMessage.value = "Registration successful"
                    Log.d("RegisterViewModel", "Registration successful")
                } else {
                    _isRegistrationSuccessful.value = false
                    _toastMessage.value = "Registration failed: ${result.exceptionOrNull()?.message}"
                    Log.d("RegisterViewModel", "Registration failed, result: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: FirebaseAuthException) {
                Log.e("RegisterViewModel", "Firebase authentication error", e)
                _isRegistrationSuccessful.value = false
                _toastMessage.value = "Firebase authentication error: ${e.message}"
            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Registration failed", e)
                _isRegistrationSuccessful.value = false
                _toastMessage.value = "Error: ${e.message}"
            }

            Log.d("RegisterViewModel", "_isRegisterSuccessful: ${_isRegistrationSuccessful.value}")
        }
    }

    fun isPasswordValid(password: String): Boolean {
        return providePasswordRegex.matches(password)
    }

    fun registerToLogin() {
        _registerToLogin.value = true
    }

    fun onRegisterToLoginComplete() {
        _registerToLogin.value = false
    }
}