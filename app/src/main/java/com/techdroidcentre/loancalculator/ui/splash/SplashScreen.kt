package com.techdroidcentre.loancalculator.ui.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techdroidcentre.loancalculator.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel()
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alpha = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        label = "Alpha",
        animationSpec = tween(3000)
    )
    LaunchedEffect(key1 = Unit) {
        startAnimation = true
        delay(3000)
        if (viewModel.currentUser != null) {
            onNavigateToHome()
        } else {
            onNavigateToSignIn()
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFF1976D2))

    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_calculate_24),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha.value),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}