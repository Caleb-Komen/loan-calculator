package com.techdroidcentre.loancalculator.ui.home.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.techdroidcentre.loancalculator.ui.theme.LoanCalculatorTheme
import java.text.NumberFormat

@Composable
fun PieChart(
    loanDetails: Pair<Double, Pair<Color, String>>,
    totalInterestDetails: Pair<Double, Pair<Color, String>>,
    totalPayableAmount: Double,
    canvasSize: Dp = 300.dp
) {
    val degree = 360
    val animatedLoanAmount = remember { Animatable(initialValue = 0f) }
    val animatedTotalInterest = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(key1 = loanDetails, key2 = totalInterestDetails) {
        animatedLoanAmount.animateTo(loanDetails.first.toFloat())
        animatedTotalInterest.animateTo(totalInterestDetails.first.toFloat())
    }

    val loanAmountPercentage = (animatedLoanAmount.value / totalPayableAmount) * 100
    val totalInterestPercentage = (animatedTotalInterest.value / totalPayableAmount) * 100

    val loanAmountSweepAngle by animateFloatAsState(
        targetValue = ((loanAmountPercentage / 100) * degree).toFloat(),
        label = "Loan amount",
        animationSpec = tween(durationMillis = 1000)
    )
    val totalInterestSweepAngle by animateFloatAsState(
        targetValue = ((totalInterestPercentage / 100) * degree).toFloat(),
        label = "Total interest"
    )
    val loanStartAngle = 150f
    val totalInterestStartAngle = loanStartAngle + loanAmountSweepAngle
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(canvasSize)
                .drawBehind {
                    val componentSize = size / 1.25f
                    val offsetX = (size.width - componentSize.width) / 2
                    val offsetY = (size.height - componentSize.height) / 2
                    drawArc(
                        color = loanDetails.second.first,
                        startAngle = 150f,
                        sweepAngle = loanAmountSweepAngle,
                        useCenter = true,
                        size = componentSize,
                        topLeft = Offset(offsetX, offsetY)
                    )
                    drawArc(
                        color = totalInterestDetails.second.first,
                        startAngle = totalInterestStartAngle,
                        sweepAngle = totalInterestSweepAngle,
                        useCenter = true,
                        size = componentSize,
                        topLeft = Offset(offsetX, offsetY)
                    )
                }
        )
        LegendEntry(
            label = loanDetails.second.second,
            color = loanDetails.second.first,
            percent = formatPercent(loanAmountPercentage/100)
        )
        LegendEntry(
            label = totalInterestDetails.second.second,
            color = totalInterestDetails.second.first,
            percent = formatPercent(totalInterestPercentage/100)
        )
    }
}

@Composable
fun LegendEntry(label: String, color: Color, percent: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(4.dp)
                .drawBehind {
                    drawRect(color = color)
                }
        )
        Text(
            text = "$label($percent)",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

private fun formatPercent(percent: Double): String {
    return NumberFormat.getPercentInstance().format(percent) ?: "0.0%"
}
