package com.techdroidcentre.loancalculator.ui.signup

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
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    fun signUp(email: String, username: String, password: String, onSuccess: ()-> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(loading = true)
            }
            authRepository.signUp(
                username = username,
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

    fun updateUsername(username: String) {
        _uiState.update {
            it.copy(username = username)
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.update {
            it.copy(confirmPassword = confirmPassword)
        }
    }

    fun updatePasswordVisibility(visible: Boolean) {
        _uiState.update {
            it.copy(passwordVisibility = visible)
        }
    }

    fun updateConfirmPasswordVisibility(visible: Boolean) {
        _uiState.update {
            it.copy(confirmPasswordVisibility = visible)
        }
    }
}
