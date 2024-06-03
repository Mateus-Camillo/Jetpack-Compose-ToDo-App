package com.example.jetpackcomposetodoapp.data

interface AuthRepository {
    suspend fun authenticate(email: String, password: String) : Result<String>
}