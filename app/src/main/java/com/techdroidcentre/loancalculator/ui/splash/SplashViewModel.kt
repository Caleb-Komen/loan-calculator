package com.techdroidcentre.loancalculator.ui.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.techdroidcentre.loancalculator.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    var currentUser: FirebaseUser? by mutableStateOf(null)
        private set

    init {
        viewModelScope.launch {
            currentUser = authRepository.getCurrentUser()
        }
    }
}