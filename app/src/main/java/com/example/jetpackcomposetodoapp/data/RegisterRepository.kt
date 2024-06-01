package com.example.jetpackcomposetodoapp.data

interface RegisterRepository {
    suspend fun register(email: String, password: String) : Result<String>
}