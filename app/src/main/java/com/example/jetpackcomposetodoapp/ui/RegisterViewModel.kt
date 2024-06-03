package com.example.jetpackcomposetodoapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposetodoapp.data.AuthRepository
import com.example.jetpackcomposetodoapp.di.Register
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    @Register private val authRepository: AuthRepository
) : ViewModel() {

    private val _showMessage = MutableLiveData<Boolean>()
    val showMessage: LiveData<Boolean> get() = _showMessage

    private val _registerToLogin = MutableLiveData<Boolean>()
    val registerToLogin: LiveData<Boolean> get() = _registerToLogin


    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.authenticate(email, password)

            if (result.isSuccess) {
                _showMessage.value = true
            } else {
                Log.e("RegisterViewModel", "Registration failed", result.exceptionOrNull())
               // TODO: Handle registration failure
            }
        }
    }

    fun registerToLogin() {
        _registerToLogin.value = true
    }

    fun onRegisterToLoginComplete() {
        _registerToLogin.value = false
    }
}