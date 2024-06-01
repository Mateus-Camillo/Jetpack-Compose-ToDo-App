package com.example.jetpackcomposetodoapp.repository

import com.example.jetpackcomposetodoapp.data.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : RegisterRepository {

    override suspend fun register(email: String, password: String) : Result<String> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(result.user?.uid ?: "User is registered but UID is null")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}