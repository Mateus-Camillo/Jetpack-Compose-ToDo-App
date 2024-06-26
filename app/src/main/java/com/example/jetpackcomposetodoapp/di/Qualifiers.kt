package com.example.jetpackcomposetodoapp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Register

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Login

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EmailRegexQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PasswordRegexQualifier
