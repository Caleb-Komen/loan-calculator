package com.techdroidcentre.loancalculator.ui.signin

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction.Companion
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techdroidcentre.loancalculator.R
import com.techdroidcentre.loancalculator.ui.theme.LoanCalculatorTheme

@Composable
fun SignInScreen(
    onNavigateToSignUp: () -> Unit,
    onSignIn: (String, String) -> Unit,
    viewModel: SignInViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        SignInScreen(
            email = uiState.email,
            password = uiState.password,
            passwordVisibility = uiState.passwordVisibility,
            onEmailChange = viewModel::updateEmail,
            onPasswordChange = viewModel::updatePassword,
            onChangePasswordVisibility = viewModel::updatePasswordVisibility,
            onSignIn = onSignIn,
            onNavigateToSignUp = onNavigateToSignUp,
            modifier = modifier
        )
        if (uiState.loading) {
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    email: String,
    password: String,
    passwordVisibility: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onChangePasswordVisibility: (Boolean) -> Unit,
    onSignIn: (String, String) -> Unit,
    onNavigateToSignUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = if (passwordVisibility) painterResource(R.drawable.baseline_visibility_24) else painterResource(
        R.drawable.baseline_visibility_off_24
    )
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxSize()
            .drawBehind {
                drawCircle(
                    color = Color(0xFF64B5F6),
                    center = Offset(20.dp.toPx(), 100.dp.toPx())
                )
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xD05EB6FC),
                            Color(0xBB87C6F8),
                            Color(0xB9D2E9FC),
                            Color(0xB2F7F9FA),
                        )
                    )
                )
                drawCircle(
                    color = Color.White,
                    center = Offset(size.width, size.height)
                )
            }.statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Sign in to continue",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Image(
                painter = painterResource(R.drawable.auth),
                contentDescription = null,
                modifier = Modifier
                    .sizeIn(maxWidth = 200.dp, maxHeight = 200.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier.weight(1f),
        ) {
            TextField(
                value = email,
                onValueChange = onEmailChange,
                placeholder = {
                    Text(text = "Email")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null)
                },
                maxLines = 1,
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor = Color.Black.copy(alpha = 0.4f),
                    unfocusedLeadingIconColor = Color.Black.copy(alpha = 0.4f),
                    placeholderColor = Color.Black.copy(alpha = 0.4f)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = Companion.Next),
                modifier = Modifier
                    .padding(8.dp)
            )
            TextField(
                value = password,
                onValueChange = onPasswordChange,
                placeholder = {
                    Text(text = "Password")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = { onChangePasswordVisibility(!passwordVisibility) }) {
                        Icon(painter = icon, contentDescription = "Password visibility")
                    }
                },
                maxLines = 1,
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor = Color.Black.copy(alpha = 0.4f),
                    unfocusedLeadingIconColor = Color.Black.copy(alpha = 0.4f),
                    placeholderColor = Color.Black.copy(alpha = 0.4f)
                ),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = Companion.Done),
                modifier = Modifier.padding(8.dp)
            )
            Button(
                onClick = {
                    if (validate(context, email, password)) {
                        onSignIn(email, password)
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Sign in")
            }
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Don't have an account?")
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable {
                            onNavigateToSignUp()
                        },
                    text = "Sign up",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

private fun validate(
    context: Context,
    email: String,
    password: String,
): Boolean {
    return when {
        !validateSignInForm(email, password) -> {
            Toast.makeText(context, "Fill out all fields", Toast.LENGTH_SHORT).show()
            false
        }
        else -> true
    }
}

private fun validateSignInForm(email: String, password: String): Boolean {
    return email.isNotEmpty() && password.isNotEmpty()
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    LoanCalculatorTheme {
        SignInScreen(
            email = "",
            password = "",
            passwordVisibility = false,
            onEmailChange = {},
            onPasswordChange = {},
            onChangePasswordVisibility = {},
            onSignIn = { _, _ -> },
            onNavigateToSignUp = {}
        )
    }
}