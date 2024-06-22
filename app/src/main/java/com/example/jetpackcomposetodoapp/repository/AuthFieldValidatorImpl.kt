package com.example.jetpackcomposetodoapp.repository

import com.example.jetpackcomposetodoapp.data.AuthFieldValidator
import javax.inject.Inject

class AuthFieldValidatorImpl @Inject constructor() : AuthFieldValidator {
    private val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$")

    override fun isEmailValid(email: String): Boolean {
        return emailRegex.matches(email)
    }

    override fun isPasswordValid(password: String): Boolean {
        return passwordRegex.matches(password)
    }

    override fun fieldsValidation(email: String, password: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }
}