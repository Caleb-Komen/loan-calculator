package com.techdroidcentre.loancalculator.data

import com.techdroidcentre.loancalculator.data.remote.FirebaseService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService
): AuthRepository {
    override suspend fun signUp(
        username: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = firebaseService.signUp(username, email, password, onSuccess, onError)

    override suspend fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        firebaseService.signIn(email, password, onSuccess, onError)
    }

    override suspend fun signOut() {
        firebaseService.signOut()
    }

    override suspend fun getCurrentUser() = firebaseService.getCurrentUser()
}