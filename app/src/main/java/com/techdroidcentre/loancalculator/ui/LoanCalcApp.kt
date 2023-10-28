package com.techdroidcentre.loancalculator.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.techdroidcentre.loancalculator.navigation.LoanCalcNavHost

@Composable
fun LoanCalcApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    LoanCalcNavHost(navController = navController, modifier = modifier)
}