package com.techdroidcentre.loancalculator.ui

const val SPLASH_SCREEN_ROUTE = "splash"
const val HOME_SCREEN_ROUTE = "home"
const val SIGNUP_SCREEN_ROUTE = "signup"
const val SIGNIN_SCREEN_ROUTE = "signin"

sealed class Screen(val route: String) {
    object SplashScreen: Screen(SPLASH_SCREEN_ROUTE)

    object SignUpScreen: Screen(SIGNUP_SCREEN_ROUTE)

    object SignInScreen: Screen(SIGNIN_SCREEN_ROUTE)

    object HomeScreen: Screen(HOME_SCREEN_ROUTE)
}
