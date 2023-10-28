package com.techdroidcentre.loancalculator.ui.home

data class HomeUiState(
    val loan: String = "",
    val interestRate: String = "",
    val term: String = "",
    val period: Period = Period.MONTHS,
    val isLoggedIn: Boolean = false,
    val error: String = "",
    val principalAmount: Double = 0.0,
    val monthlyPayment: Double = 0.0,
    val totalPayableAmount: Double = 0.0,
    val totalInterest: Double = 0.0,
    val resultsVisibility: Boolean = false
)

enum class Period {
    MONTHS,
    YEARS
}
