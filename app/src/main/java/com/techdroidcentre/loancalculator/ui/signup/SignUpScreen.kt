package com.techdroidcentre.loancalculator.ui.signup

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.techdroidcentre.loancalculator.R
import com.techdroidcentre.loancalculator.model.Result

@Composable
fun SignUpScreen(
    onNavigateToSignIn: () -> Unit,
    onSignUp: (String, String, String) -> Unit,
    viewModel: SignUpViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        SignUpScreen(
            email = uiState.email,
            username = uiState.username,
            password = uiState.password,
            confirmPassword = uiState.confirmPassword,
            passwordVisibility = uiState.passwordVisibility,
            confirmPasswordVisibility = uiState.confirmPasswordVisibility,
            onEmailChange = viewModel::updateEmail,
            onUsernameChange = viewModel::updateUsername,
            onPasswordChange = viewModel::updatePassword,
            onConfirmPasswordChange = viewModel::updateConfirmPassword,
            onChangePasswordVisibility = viewModel::updatePasswordVisibility,
            onChangeConfirmPasswordVisibility = viewModel::updateConfirmPasswordVisibility,
            onSignUp = onSignUp,
            onNavigateToSignIn = onNavigateToSignIn
        )
        if (uiState.loading) {
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    email: String,
    username: String,
    password: String,
    confirmPassword: String,
    passwordVisibility: Boolean,
    confirmPasswordVisibility: Boolean,
    onEmailChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onChangePasswordVisibility: (Boolean) -> Unit,
    onChangeConfirmPasswordVisibility: (Boolean) -> Unit,
    onSignUp: (String, String, String) -> Unit,
    onNavigateToSignIn: () -> Unit,
    modifier: Modifier = Modifier
) {
    val passwordIcon = if (passwordVisibility) painterResource(R.drawable.baseline_visibility_24) else painterResource(
        R.drawable.baseline_visibility_off_24
    )

    val confirmPasswordIcon = if (confirmPasswordVisibility) painterResource(R.drawable.baseline_visibility_24) else painterResource(
        R.drawable.baseline_visibility_off_24
    )

    val context = LocalContext.current

    LazyColumn(
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
        item {
            Column {
                Text(
                    text = "Sign up",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                Image(
                    painter = painterResource(R.drawable.auth),
                    contentDescription = null,
                    modifier = modifier
                        .sizeIn(maxWidth = 200.dp, maxHeight = 200.dp)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }
        }
        item {
            Column {
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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                    modifier = Modifier
                        .padding(8.dp)
                )
                TextField(
                    value = username,
                    onValueChange = onUsernameChange,
                    placeholder = {
                        Text(text = "Username")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null)
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
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    modifier = Modifier.padding(8.dp)
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
                            Icon(painter = passwordIcon, contentDescription = "Password visibility")
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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                    modifier = Modifier.padding(8.dp)
                )
                TextField(
                    value = confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    placeholder = {
                        Text(text = "Confirm password")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                    },
                    trailingIcon = {
                        IconButton(onClick = { onChangeConfirmPasswordVisibility(!confirmPasswordVisibility) }) {
                            Icon(painter = confirmPasswordIcon, contentDescription = "Password visibility")
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
                    visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                    modifier = Modifier.padding(8.dp)
                )
                Button(
                    onClick = {
                        if (validate(context, username, email, password, confirmPassword)) {
                            onSignUp(email.trim(), username.trim(), password.trim())
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Sign up")
                }
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Already have an account?")
                    Text(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .clickable {
                                onNavigateToSignIn()
                            },
                        text = "Sign in",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

private fun validate(
    context: Context,
    username: String,
    email: String,
    password: String,
    confirmPassword: String
): Boolean {
    return when {
        !validateSignUpForm(username, email, password, confirmPassword) -> {
            Toast.makeText(context, "Fill out all fields", Toast.LENGTH_SHORT).show()
            false
        }

        !isEmailValid(email) -> {
            Toast.makeText(context, "Enter a valid email address", Toast.LENGTH_SHORT).show()
            false
        }

        !isPasswordValid(password) -> {
            Toast.makeText(context, "Password must have more than 5 characters", Toast.LENGTH_SHORT).show()
            false
        }

        password != confirmPassword -> {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            false
        }

        else -> true
    }
}

private fun validateSignUpForm(username: String, email: String, password: String, confirmPassword: String): Boolean {
        return username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()
}

private fun isEmailValid(email: String): Boolean {
    return email.contains("@")
}

private fun isPasswordValid(password: String) = password.length > 5

