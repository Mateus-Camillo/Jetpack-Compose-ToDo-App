package com.example.jetpackcomposetodoapp.data

interface AuthFieldValidator {
    fun isEmailValid(email: String): Boolean
    fun isPasswordValid(password: String): Boolean
    fun fieldsValidation(email: String, password: String): Boolean
}