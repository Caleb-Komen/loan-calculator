package com.techdroidcentre.loancalculator.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.techdroidcentre.loancalculator.ui.Screen
import com.techdroidcentre.loancalculator.ui.home.HomeScreen
import com.techdroidcentre.loancalculator.ui.home.HomeViewModel
import com.techdroidcentre.loancalculator.ui.signin.SignInScreen
import com.techdroidcentre.loancalculator.ui.signin.SignInViewModel
import com.techdroidcentre.loancalculator.ui.signup.SignUpScreen
import com.techdroidcentre.loancalculator.ui.signup.SignUpViewModel
import com.techdroidcentre.loancalculator.ui.splash.SplashScreen

@Composable
fun LoanCalcNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = modifier
    ) {
        composable(
            route = Screen.SplashScreen.route
        ) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToSignIn = {
                    navController.navigate(Screen.SignInScreen.route) {
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = Screen.HomeScreen.route
        ) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                onNavigateToSignIn = {
                    navController.navigate(Screen.SignInScreen.route) {
                        popUpTo(Screen.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onSignOut = {
                    viewModel.signOut()
                    navController.navigate(Screen.SignInScreen.route) {
                        popUpTo(Screen.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                },
                viewModel = viewModel
            )
        }
        composable(
            route = Screen.SignUpScreen.route
        ) {
            val viewModel: SignUpViewModel = hiltViewModel()
            SignUpScreen(
                onNavigateToSignIn = {
                    navController.navigate(Screen.SignInScreen.route) {
                        popUpTo(Screen.SignInScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onSignUp = { email, username, password ->
                    viewModel.signUp(
                        email = email,
                        username = username,
                        password = password,
                        onSuccess = {
                            navController.navigate(Screen.HomeScreen.route) {
                                popUpTo(Screen.SignInScreen.route) {
                                    inclusive = true
                                }
                            }
                        },
                        onError = {
                            if (it.isNotEmpty()) {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                },
                viewModel = viewModel
            )
        }
        composable(
            route = Screen.SignInScreen.route
        ) {
            val viewModel: SignInViewModel = hiltViewModel()
            SignInScreen(
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUpScreen.route)
                },
                onSignIn = { email, password ->
                    viewModel.signIn(
                        email = email,
                        password = password,
                        onSuccess = {
                            navController.navigate(Screen.HomeScreen.route) {
                                popUpTo(Screen.SignInScreen.route) {
                                    inclusive = true
                                }
                            }
                        },
                        onError = {
                            if (it.isNotEmpty()) {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                },
                viewModel = viewModel
            )
        }
    }
}