package com.example.jetpackcomposetodoapp.data

interface LoginRepository {
    suspend fun loginUser(email: String, password: String): Result<String>
    suspend fun signOutUser(): Result<String>
}