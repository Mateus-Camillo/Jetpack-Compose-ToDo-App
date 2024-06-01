package com.example.jetpackcomposetodoapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposetodoapp.data.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _showMessage = MutableLiveData<Boolean>()
    val showMessage: LiveData<Boolean> get() = _showMessage

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            val result = registerRepository.register(email, password)

            if (result.isSuccess) {
                _showMessage.value = true
            } else {
                Log.e("RegisterViewModel", "Registration failed", result.exceptionOrNull())
               // TODO: Handle registration failure
            }
        }
    }
}