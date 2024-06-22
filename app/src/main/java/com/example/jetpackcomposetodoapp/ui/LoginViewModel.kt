package com.example.jetpackcomposetodoapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposetodoapp.data.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                loginRepository.loginUser(email, password)

                val currentUser = firebaseAuth.currentUser

                Log.d("LoginViewModel", "Login Successful: ${currentUser?.email}")

                delay(5000L)

                val signOut = loginRepository.signOutUser()

                if(signOut.isSuccess) {
                   Log.d("LoginViewModel", "Sign Out Successful")
                } else {
                    Log.e("LoginViewModel", "Sign Out Failed")
                }

            } catch (e: Exception) {
                Log.e("LoginViewModel", "Login failed: ${e.message}")
            }
        }
    }

}