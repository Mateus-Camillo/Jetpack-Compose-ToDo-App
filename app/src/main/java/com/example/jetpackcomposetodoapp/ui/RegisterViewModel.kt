package com.example.jetpackcomposetodoapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposetodoapp.data.AuthExceptionRepository
import com.example.jetpackcomposetodoapp.data.AuthRepository
import com.example.jetpackcomposetodoapp.di.Register
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val providePasswordRegex: Regex,
    @Register private val authExceptionRepository: AuthExceptionRepository
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
            authExceptionRepository.firebaseAuthException(
                email,
                password,
                _isRegistrationSuccessful,
                _toastMessage)
        }

    }

    fun isPasswordValid(password: String): Boolean {
        return providePasswordRegex.matches(password)
    }
}