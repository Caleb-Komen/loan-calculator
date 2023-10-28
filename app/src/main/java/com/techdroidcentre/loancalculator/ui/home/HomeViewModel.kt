package com.techdroidcentre.loancalculator.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.techdroidcentre.loancalculator.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.NumberFormat
import javax.inject.Inject
import kotlin.math.pow

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    var currentUser: FirebaseUser? by mutableStateOf(null)
        private set

    init {
        viewModelScope.launch {
            currentUser = authRepository.getCurrentUser()
        }
    }

    fun signOut() = viewModelScope.launch {
        authRepository.signOut()
    }

    fun updateLoan(loan: String){
        _uiState.update {
            it.copy(loan = loan)
        }
    }

    fun updateInterestRate(interestRate: String){
        _uiState.update {
            it.copy(interestRate = interestRate)
        }
    }

    fun updateTerm(term: String){
        _uiState.update {
            it.copy(term = term)
        }
    }

    fun updatePeriod(period: Period){
        _uiState.update {
            it.copy(period = period)
        }
    }

    fun calculate() {
        try {
            val loan = _uiState.value.loan.toDouble()
            val interestRate = _uiState.value.interestRate.toFloat()
            val term = _uiState.value.term.toInt()
            val monthlyPayment = getMonthlyPayment(loan, interestRate, term)
            val totalPayableAmount = getTotalPayableAmount(monthlyPayment, term)
            val totalInterest = totalPayableAmount - loan
            _uiState.update {
                it.copy(
                    principalAmount = loan,
                    monthlyPayment = monthlyPayment,
                    totalPayableAmount = totalPayableAmount,
                    totalInterest = totalInterest,
                    error = "",
                    resultsVisibility = true
                )
            }
        } catch (ex: NumberFormatException) {
            Log.d(TAG, "calculate: ----- $ex ---")
            _uiState.update {
                it.copy(error = ex.localizedMessage ?: "Something went wrong")
            }
        }
    }

    fun reset() {
        updateLoan("")
        updateInterestRate("")
        updateTerm("")
        updatePeriod(Period.MONTHS)
    }

    private fun getMonthlyPayment(loan: Double, interestRate: Float, term: Int): Double {
        val termsInMonths = if (_uiState.value.period == Period.YEARS) term * MONTHS_IN_YEARS else term
        val monthlyRate = (interestRate / 100) / MONTHS_IN_YEARS
        val numeratorValue = monthlyRate * (1 + monthlyRate).pow(termsInMonths) // r(1 + r)^n
        val denominatorValue = (1 + monthlyRate).pow(termsInMonths) - 1 // (1 + r)^n - 1
        return loan * (numeratorValue / denominatorValue).toDouble()
    }

    private fun getTotalPayableAmount(monthlyPayment: Double, term: Int): Double {
        val termsInMonths = if (_uiState.value.period == Period.YEARS) term * MONTHS_IN_YEARS else term
        return monthlyPayment * termsInMonths
    }

    companion object {
        const val TAG = "HomeViewModel"
        const val MONTHS_IN_YEARS = 12
    }
}