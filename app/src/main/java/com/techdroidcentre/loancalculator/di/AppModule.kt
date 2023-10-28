package com.techdroidcentre.loancalculator.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.techdroidcentre.loancalculator.data.AuthRepository
import com.techdroidcentre.loancalculator.data.AuthRepositoryImpl
import com.techdroidcentre.loancalculator.data.remote.FirebaseService
import com.techdroidcentre.loancalculator.data.remote.FirebaseServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Singleton
    @Provides
    fun provideFirebaseService(
        auth: FirebaseAuth
    ): FirebaseService {
        return FirebaseServiceImpl(auth)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        firebaseService: FirebaseService
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseService)
    }
}