package com.example.jetpackcomposetodoapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.jetpackcomposetodoapp.data.AuthExceptionRepository
import com.example.jetpackcomposetodoapp.data.RegisterRepository
import com.google.firebase.auth.FirebaseAuthException
import javax.inject.Inject

class AuthExceptionRepositoryImpl @Inject constructor(
    private val registerRepository: RegisterRepository
) : AuthExceptionRepository {
    override suspend fun firebaseAuthException(authenticationResult: Result<String>, isSuccessful: MutableLiveData<Boolean>, toastMessage: MutableLiveData<CharSequence>) {

        try {
            Log.d("RegisterViewModel", "Starting authentication")
            Log.d("RegisterViewModel", "Authentication authenticationResult received: $authenticationResult")

            if (authenticationResult.isSuccess) {
                isSuccessful.value = true
                toastMessage.value = "Registration successful"
                Log.d("RegisterViewModel", "Registration successful")
            } else {
                isSuccessful.value = false
                toastMessage.value = "Registration failed: ${authenticationResult.exceptionOrNull()?.message}"
                Log.d("RegisterViewModel", "Registration failed, authenticationResult: ${authenticationResult.exceptionOrNull()?.message}")
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