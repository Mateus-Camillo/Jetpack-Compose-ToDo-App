package com.example.jetpackcomposetodoapp.data

interface RegisterRepository {
    suspend fun registerUser(email: String, password: String) : Result<String>
}