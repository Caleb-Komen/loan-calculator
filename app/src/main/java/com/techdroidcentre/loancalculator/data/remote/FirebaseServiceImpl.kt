package com.techdroidcentre.loancalculator.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.techdroidcentre.loancalculator.model.Result
import com.techdroidcentre.loancalculator.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseServiceImpl @Inject constructor(
    private val auth: FirebaseAuth
): FirebaseService {
    override suspend fun signUp(
        username: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val firebaseUser = auth.currentUser!!
                firebaseUser.updateProfile(
                    UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        onSuccess()
                    } else {
                        onError(task.exception?.localizedMessage ?: "Something went wrong")
                    }
                }
            } else {
                onError(task.exception?.localizedMessage ?: "Something went wrong")
            }
        }
    }

    override suspend fun signIn(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onError(task.exception?.localizedMessage ?: "Something went wrong")
            }
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun getCurrentUser() = auth.currentUser
}