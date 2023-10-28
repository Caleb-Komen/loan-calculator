package com.techdroidcentre.loancalculator.data

import com.google.firebase.auth.FirebaseUser
import com.techdroidcentre.loancalculator.model.Result
import com.techdroidcentre.loancalculator.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
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
