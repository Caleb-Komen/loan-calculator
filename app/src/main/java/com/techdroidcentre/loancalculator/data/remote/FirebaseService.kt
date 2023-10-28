package com.techdroidcentre.loancalculator.data.remote

import com.google.firebase.auth.FirebaseUser

interface FirebaseService {
    suspend fun signUp(
        username: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
    suspend fun signOut()
    suspend fun getCurrentUser(): FirebaseUser?
}
