package com.techdroidcentre.loancalculator.ui.signup

import com.techdroidcentre.loancalculator.model.User

data class SignUpUiState(
    val user: User = User(),
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val passwordVisibility: Boolean = false,
    val confirmPasswordVisibility: Boolean = false,
    val loading: Boolean = false
)
