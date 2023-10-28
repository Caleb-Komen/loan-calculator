package com.techdroidcentre.loancalculator.ui.signin

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val passwordVisibility: Boolean = false,
    val loading: Boolean = false,
)
