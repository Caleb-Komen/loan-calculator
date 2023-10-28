package com.techdroidcentre.loancalculator.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techdroidcentre.loancalculator.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    fun signIn(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(loading = true)
            }
            authRepository.signIn(
                email = email,
                password = password,
                onSuccess = {
                    _uiState.update {
                        it.copy(loading = false)
                    }
                    onSuccess()
                },
                onError = { error ->
                    _uiState.update {
                        it.copy(loading = false)
                    }
                    onError(error)
                }
            )
        }
    }

    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun updatePasswordVisibility(visible: Boolean) {
        _uiState.update {
            it.copy(passwordVisibility = visible)
        }
    }
}