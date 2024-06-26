package com.example.jetpackcomposetodoapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposetodoapp.data.AuthExceptionRepository
import com.example.jetpackcomposetodoapp.data.AuthFieldValidator
import com.example.jetpackcomposetodoapp.data.RegisterRepository
import com.example.jetpackcomposetodoapp.di.Register
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository,
    @Register private val authExceptionRepository: AuthExceptionRepository,

    private val authFieldValidator: AuthFieldValidator,

    @Named("PasswordRegex") private val providePasswordRegex: Regex,
    @Named("EmailRegex") private val provideEmailRegex: Regex
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

            val registerResult = registerRepository.registerUser(email, password)

            authExceptionRepository.firebaseAuthException(
                registerResult,
                _isRegistrationSuccessful,
                _toastMessage)
        }

    }

    fun isEmailValid(email: String): Boolean {
        return provideEmailRegex.matches(email)
    }

    fun areRegisterFieldsValid(email: String, password: String): Boolean {
        return authFieldValidator.fieldsValidation(email, password)
    }

    fun areFieldsEmpty(vararg fields: String): Boolean {
        return fields.all { it.isNotEmpty() }
    }
}