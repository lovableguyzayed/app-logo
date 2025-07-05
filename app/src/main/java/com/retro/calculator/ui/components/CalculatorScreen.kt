package com.retro.calculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.retro.calculator.data.CalculatorState
import com.retro.calculator.data.CalculatorStep
import com.retro.calculator.data.UnitType
import com.retro.calculator.ui.theme.RetroColors
import com.retro.calculator.ui.theme.RetroTypography
import com.retro.calculator.utils.HapticFeedback

@Composable
fun CalculatorScreen() {
    val context = LocalContext.current
    val hapticFeedback = remember { HapticFeedback(context) }
    var calculatorState by remember { mutableStateOf(CalculatorState()) }
    
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        RetroColors.BgDark,
                        RetroColors.BgCard,
                        RetroColors.BgDark
                    )
                )
            )
    ) {
        // Left Panel - Robot
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            RetroColors.BgDark,
                            RetroColors.BgCard
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Robot Character
                RobotCharacter(
                    isExcited = calculatorState.step == CalculatorStep.RESULT,
                    showConfirmAnimation = calculatorState.showConfirmAnimation
                )
                
                // Robot Chat Bubble
                if (calculatorState.robotMessage.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .widthIn(max = 200.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = RetroColors.Primary
                        ),
                        shape = RoundedCornerShape(
                            topStart = 15.dp,
                            topEnd = 15.dp,
                            bottomStart = 5.dp,
                            bottomEnd = 15.dp
                        )
                    ) {
                        Text(
                            text = calculatorState.robotMessage,
                            style = RetroTypography.bodyMedium.copy(
                                color = Color.White,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }
        
        // Right Panel - Calculator
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = RetroColors.BgCard
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Text(
                    text = "RETRO CALCULATOR",
                    style = RetroTypography.headlineLarge.copy(
                        color = RetroColors.Accent,
                        fontSize = 16.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Divider(
                    color = RetroColors.Primary.copy(alpha = 0.3f),
                    thickness = 1.dp
                )
                
                // Calculator Steps
                when (calculatorState.step) {
                    CalculatorStep.QUANTITY -> {
                        QuantityInput(
                            state = calculatorState,
                            onStateChange = { newState ->
                                calculatorState = newState
                                hapticFeedback.performHapticFeedback()
                            }
                        )
                    }
                    CalculatorStep.UNIT -> {
                        UnitSelection(
                            state = calculatorState,
                            onStateChange = { newState ->
                                calculatorState = newState
                                hapticFeedback.performHapticFeedback()
                            }
                        )
                    }
                    CalculatorStep.PRICE -> {
                        PriceInput(
                            state = calculatorState,
                            onStateChange = { newState ->
                                calculatorState = newState
                                hapticFeedback.performHapticFeedback()
                            }
                        )
                    }
                    CalculatorStep.RESULT -> {
                        ResultDisplay(
                            state = calculatorState,
                            onReset = {
                                calculatorState = CalculatorState()
                                hapticFeedback.performHapticFeedback()
                            }
                        )
                    }
                }
            }
        }
    }
}