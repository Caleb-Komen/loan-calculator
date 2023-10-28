package com.techdroidcentre.loancalculator.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techdroidcentre.loancalculator.R
import com.techdroidcentre.loancalculator.ui.home.component.PieChart
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HomeScreen(
    onNavigateToSignIn: () -> Unit,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    val uiState: HomeUiState by viewModel.uiState.collectAsState()
    var dropdownExpanded by remember { mutableStateOf(false) }

    if (viewModel.currentUser == null) {
        onNavigateToSignIn()
    }

    Column(
        modifier = modifier.statusBarsPadding()
    ) {
        TopAppBar(
            username = viewModel.currentUser?.displayName ?: "",
            onSignOut = onSignOut
        )
        HomeScreen(
            uiState = uiState,
            dropdownExpanded = dropdownExpanded,
            onLoanChange = viewModel::updateLoan,
            onInterestRateChange = viewModel::updateInterestRate,
            onTermChange = viewModel::updateTerm,
            onDropDownMenuClick = { dropdownExpanded = true },
            onDropDownMenuItemClick = {
                viewModel.updatePeriod(Period.valueOf(it))
                dropdownExpanded = false
            },
            onDismissRequest = { dropdownExpanded = false },
            onCalculate = viewModel::calculate,
            onReset = viewModel::reset
        )
    }

}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    dropdownExpanded: Boolean,
    onLoanChange: (String) -> Unit,
    onInterestRateChange: (String) -> Unit,
    onTermChange: (String) -> Unit,
    onDropDownMenuClick: () -> Unit,
    onDropDownMenuItemClick: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onCalculate: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(24.dp)
    ) {
        item {
            Column {
                AnimatedVisibility(visible = uiState.resultsVisibility) {
                    Statistics(uiState = uiState)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        item {
            LoanForm(
                uiState = uiState,
                dropdownExpanded = dropdownExpanded,
                onLoanChange = onLoanChange,
                onInterestRateChange = onInterestRateChange,
                onTermChange = onTermChange,
                onDropDownMenuClick = onDropDownMenuClick,
                onDropDownMenuItemClick = onDropDownMenuItemClick,
                onDismissRequest = onDismissRequest,
                onCalculate = onCalculate,
                onReset = onReset
            )
        }
    }
}

@Composable
fun Statistics(
    uiState: HomeUiState,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        shadowElevation = 4.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = formatCurrency(uiState.monthlyPayment),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "EMI per month",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            }
            PieChart(
                loanDetails = uiState.principalAmount to (Color(0xFF42A5F5) to "Loan Amount"),
                totalInterestDetails = uiState.totalInterest to (Color(0xFF10DF56) to "Total interest"),
                totalPayableAmount = uiState.totalPayableAmount,
                canvasSize = 200.dp
            )
            Column {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                            append("Monthly payment: ")
                        }
                        append(formatCurrency(uiState.monthlyPayment))
                    },
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                            append("Total interest: ")
                        }
                        append(formatCurrency(uiState.totalInterest))
                    },
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                            append("Total payable: ")
                        }
                        append(formatCurrency(uiState.totalPayableAmount))
                    },
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanForm(
    uiState: HomeUiState,
    dropdownExpanded: Boolean,
    onLoanChange: (String) -> Unit,
    onInterestRateChange: (String) -> Unit,
    onTermChange: (String) -> Unit,
    onDropDownMenuClick: () -> Unit,
    onDropDownMenuItemClick: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onCalculate: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        shadowElevation = 4.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Enter loan amount(Ksh):",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            TextField(
                value = uiState.loan,
                onValueChange = onLoanChange,
                placeholder = {
                    Text(text = "e.g 3000")
                },
                maxLines = 1,
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    placeholderColor = Color.Black.copy(alpha = 0.4f)
                ),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Enter annual interest rate(%):",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            TextField(
                value = uiState.interestRate,
                onValueChange = onInterestRateChange,
                placeholder = {
                    Text(text = "e.g 1.3")
                },
                maxLines = 1,
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    placeholderColor = Color.Black.copy(alpha = 0.4f)
                ),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Enter term (Years/Months):",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = uiState.term,
                    onValueChange = onTermChange,
                    placeholder = {
                        Text(text = "e.g 2")
                    },
                    maxLines = 1,
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        placeholderColor = Color.Black.copy(alpha = 0.4f)
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .widthIn(max = 150.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
                )
                Row(
                    modifier = Modifier
                        .clickable { onDropDownMenuClick() }
                        .widthIn(max = 150.dp)
                        .background(Color.White, MaterialTheme.shapes.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = uiState.period.name.lowercase().replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                        })
                        PeriodDropdown(
                            dropdownExpanded = dropdownExpanded,
                            onDropDownMenuItemClick = onDropDownMenuItemClick,
                            onDismissRequest = onDismissRequest
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Open dropdown menu"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    onClick = onReset,
                    enabled = uiState.loan.isNotEmpty() || uiState.interestRate.isNotEmpty() || uiState.term.isNotEmpty(),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Reset")
                }
                Spacer(modifier = Modifier.weight(0.5f))
                Button(
                    onClick = {
                        onCalculate()
                    },
                    enabled = uiState.loan.isNotEmpty() && uiState.interestRate.isNotEmpty() && uiState.term.isNotEmpty(),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Calculate")
                }
            }
        }
    }
}

@Composable
fun PeriodDropdown(
    dropdownExpanded: Boolean,
    onDropDownMenuItemClick: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    val periods = Period.values().map { it.toString() }
    DropdownMenu(
        expanded = dropdownExpanded,
        onDismissRequest = onDismissRequest
    ) {
        periods.forEach { period ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = period.lowercase().replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                        }
                    )
                },
                onClick = {
                    onDropDownMenuItemClick(period)
                }
            )
        }
    }
}

@Composable
private fun TopAppBar(
    username: String,
    onSignOut: () -> Unit
) {
    var actionMenuExpanded by remember { mutableStateOf(false) }
    val name = username.trim().split(" ")
    val formattedName = if (name.size > 1) "${name[0].lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }} ${name[1][0].uppercase()}." else name[0]

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp, end = 8.dp)
                .size(36.dp)
        )
        Text(
            text = formattedName,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.weight(1f))
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.padding(end = 16.dp)
        ){
            IconButton(
                onClick = {
                    actionMenuExpanded = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More"
                )
            }
            TopAppBarDropdown(
                actionMenuExpanded = actionMenuExpanded,
                onSignOut = {
                    actionMenuExpanded = false
                    onSignOut()
                },
                onDismissActionMenu = {
                    actionMenuExpanded = false
                }
            )
        }
    }
}

@Composable
fun TopAppBarDropdown(
    actionMenuExpanded: Boolean,
    onSignOut: () -> Unit,
    onDismissActionMenu: () -> Unit
) {
    DropdownMenu(
        expanded = actionMenuExpanded,
        onDismissRequest = onDismissActionMenu
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = "Sign out"
                )
            },
            onClick = onSignOut
        )
    }
}

private fun formatCurrency(amount: Double): String {
    return NumberFormat.getCurrencyInstance().format(amount).trim().replaceFirstChar { "Ksh " }
}
