package com.example.jetpackcomposetodoapp.di

import com.example.jetpackcomposetodoapp.data.RegisterRepository
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

    @Provides
    @Singleton
    fun provideRegisterRepository(
        firebaseAuth: FirebaseAuth
    ) : RegisterRepository = RegisterRepositoryImpl(firebaseAuth)
}