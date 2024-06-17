package com.example.jetpackcomposetodoapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.jetpackcomposetodoapp.data.AuthExceptionRepository
import com.example.jetpackcomposetodoapp.data.AuthRepository
import com.google.firebase.auth.FirebaseAuthException
import javax.inject.Inject

class AuthExceptionRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository
) : AuthExceptionRepository {
    override suspend fun firebaseAuthException(email: String, password: String, isSuccessful: MutableLiveData<Boolean>, toastMessage: MutableLiveData<CharSequence>) {

        try {
            Log.d("RegisterViewModel", "Starting authentication")
            val result = authRepository.authenticate(email, password)
            Log.d("RegisterViewModel", "Authentication result received: $result")

            if (result.isSuccess) {
                isSuccessful.value = true
                toastMessage.value = "Registration successful"
                Log.d("RegisterViewModel", "Registration successful")
            } else {
                isSuccessful.value = false
                toastMessage.value = "Registration failed: ${result.exceptionOrNull()?.message}"
                Log.d("RegisterViewModel", "Registration failed, result: ${result.exceptionOrNull()?.message}")
            }
        } catch (e: FirebaseAuthException) {
            Log.e("RegisterViewModel", "Firebase authentication error", e)
            isSuccessful.value = false
            toastMessage.value = "Firebase authentication error: ${e.message}"
        } catch (e: Exception) {
            Log.e("RegisterViewModel", "Registration failed", e)
            isSuccessful.value = false
            toastMessage.value = "Error: ${e.message}"
        }

        Log.d("RegisterViewModel", "_isRegisterSuccessful: $isSuccessful")
    } // TODO: Analyse toastMessage parameter, check for improvements
}