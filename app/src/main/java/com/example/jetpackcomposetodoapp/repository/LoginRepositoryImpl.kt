package com.example.jetpackcomposetodoapp.repository

import com.example.jetpackcomposetodoapp.data.LoginRepository
import com.example.jetpackcomposetodoapp.data.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : LoginRepository {

    override suspend fun loginUser(email: String, password: String): Result<String> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user?.uid ?: "User is logged but UID is null")
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOutUser(): Result<String> {
        return try {
            firebaseAuth.signOut()
            Result.success("User signed out successfully")
        } catch(e: Exception) {
            Result.failure(e)
        }
    }
}