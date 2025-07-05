package com.retro.calculator.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.retro.calculator.ui.components.SplashScreen
import com.retro.calculator.ui.components.CalculatorScreen

@Composable
fun MainScreen() {
    var showCalculator by remember { mutableStateOf(false) }
    
    if (showCalculator) {
        CalculatorScreen()
    } else {
        SplashScreen(
            onEnterApp = { showCalculator = true }
        )
    }
}