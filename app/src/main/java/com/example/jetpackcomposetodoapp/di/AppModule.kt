package com.example.jetpackcomposetodoapp.di

import com.example.jetpackcomposetodoapp.data.AuthExceptionRepository
import com.example.jetpackcomposetodoapp.data.AuthRepository
import com.example.jetpackcomposetodoapp.repository.AuthExceptionRepositoryImpl
import com.example.jetpackcomposetodoapp.repository.LoginRepositoryImpl
import com.example.jetpackcomposetodoapp.repository.RegisterRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Register
    @Provides
    @Singleton
    fun provideRegisterRepository(
        firebaseAuth: FirebaseAuth
    ) : AuthRepository = RegisterRepositoryImpl(firebaseAuth)

    @Login
    @Provides
    @Singleton
    fun provideLoginRepository(
        firebaseAuth: FirebaseAuth
    ) : AuthRepository = LoginRepositoryImpl(firebaseAuth)

    @Provides
    @Singleton
    @Named("PasswordRegex")
    fun providePasswordRegex(): Regex {
        return Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$")
    }

    @Provides
    @Singleton
    @Named("EmailRegex")
    fun provideEmailRegex() : Regex {
        return Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    } // TODO: Improve giant regex

    @Provides
    @Singleton
    fun provideAuthFieldValidator() : AuthFieldValidator = AuthFieldValidatorImpl()

    @Register
    @Provides
    @Singleton
    fun provideAuthExceptionRepository(
        @Register registerRepository: AuthRepository,
    ): AuthExceptionRepository = AuthExceptionRepositoryImpl(registerRepository)

    @Login
    @Provides
    @Singleton
    fun provideLoginAuthExceptionRepository(
        @Login loginRepository: AuthRepository,
    ): AuthExceptionRepository = AuthExceptionRepositoryImpl(loginRepository)
}