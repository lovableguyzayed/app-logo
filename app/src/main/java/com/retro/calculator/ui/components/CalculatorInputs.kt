package com.retro.calculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.retro.calculator.data.*
import com.retro.calculator.ui.theme.RetroColors
import com.retro.calculator.ui.theme.RetroTypography
import kotlinx.coroutines.delay

@Composable
fun QuantityInput(
    state: CalculatorState,
    onStateChange: (CalculatorState) -> Unit
) {
    var quantityText by remember { mutableStateOf("") }
    
    LaunchedEffect(Unit) {
        onStateChange(
            state.copy(
                robotMessage = "Hello! I'm your retro calculator assistant. Let's start by entering the quantity you want to calculate."
            )
        )
    }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "STEP 1: ENTER QUANTITY",
            style = RetroTypography.headlineLarge.copy(
                color = RetroColors.Accent,
                fontSize = 14.sp
            )
        )
        
        OutlinedTextField(
            value = quantityText,
            onValueChange = { quantityText = it },
            label = { Text("Quantity", style = RetroTypography.bodyMedium) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = RetroColors.Accent,
                unfocusedBorderColor = RetroColors.Primary,
                focusedLabelColor = RetroColors.Accent,
                unfocusedLabelColor = RetroColors.Primary,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )
        
        Button(
            onClick = {
                val quantity = quantityText.toDoubleOrNull()
                if (quantity != null && quantity > 0) {
                    onStateChange(
                        state.copy(
                            quantity = quantity,
                            step = CalculatorStep.UNIT,
                            robotMessage = "Great! Now let's select the unit for your quantity."
                        )
                    )
                }
            },
            enabled = quantityText.toDoubleOrNull()?.let { it > 0 } == true,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = RetroColors.Primary,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "NEXT →",
                style = RetroTypography.labelLarge
            )
        }
    }
}

@Composable
fun UnitSelection(
    state: CalculatorState,
    onStateChange: (CalculatorState) -> Unit
) {
    var selectedUnit by remember { mutableStateOf<UnitType?>(null) }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "STEP 2: SELECT UNIT",
            style = RetroTypography.headlineLarge.copy(
                color = RetroColors.Accent,
                fontSize = 14.sp
            )
        )
        
        Text(
            text = "Quantity: ${state.quantity}",
            style = RetroTypography.bodyMedium.copy(
                color = RetroColors.Primary
            )
        )
        
        LazyColumn(
            modifier = Modifier.height(200.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(UnitType.entries) { unit ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedUnit = unit
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedUnit == unit) 
                            RetroColors.Primary.copy(alpha = 0.3f) 
                        else 
                            RetroColors.BgDark.copy(alpha = 0.5f)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = unit.displayName,
                        style = RetroTypography.bodyMedium.copy(
                            color = if (selectedUnit == unit) RetroColors.Accent else Color.White
                        ),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    onStateChange(
                        state.copy(
                            step = CalculatorStep.QUANTITY,
                            robotMessage = "Let's go back and adjust the quantity."
                        )
                    )
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RetroColors.BgDark,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("← BACK", style = RetroTypography.labelLarge)
            }
            
            Button(
                onClick = {
                    selectedUnit?.let { unit ->
                        onStateChange(
                            state.copy(
                                unit = unit,
                                step = CalculatorStep.PRICE,
                                robotMessage = "Perfect! Now enter the total price for ${state.quantity} ${unit.displayName}."
                            )
                        )
                    }
                },
                enabled = selectedUnit != null,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RetroColors.Primary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("NEXT →", style = RetroTypography.labelLarge)
            }
        }
    }
}

@Composable
fun PriceInput(
    state: CalculatorState,
    onStateChange: (CalculatorState) -> Unit
) {
    var priceText by remember { mutableStateOf("") }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "STEP 3: ENTER TOTAL PRICE",
            style = RetroTypography.headlineLarge.copy(
                color = RetroColors.Accent,
                fontSize = 14.sp
            )
        )
        
        Text(
            text = "Quantity: ${state.quantity} ${state.unit?.displayName}",
            style = RetroTypography.bodyMedium.copy(
                color = RetroColors.Primary
            )
        )
        
        OutlinedTextField(
            value = priceText,
            onValueChange = { priceText = it },
            label = { Text("Total Price (₹)", style = RetroTypography.bodyMedium) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = RetroColors.Accent,
                unfocusedBorderColor = RetroColors.Primary,
                focusedLabelColor = RetroColors.Accent,
                unfocusedLabelColor = RetroColors.Primary,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    onStateChange(
                        state.copy(
                            step = CalculatorStep.UNIT,
                            robotMessage = "Let's go back and select a different unit."
                        )
                    )
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RetroColors.BgDark,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("← BACK", style = RetroTypography.labelLarge)
            }
            
            Button(
                onClick = {
                    val price = priceText.toDoubleOrNull()
                    if (price != null && price > 0) {
                        val unitPrice = price / state.quantity
                        onStateChange(
                            state.copy(
                                totalPrice = price,
                                unitPrice = unitPrice,
                                step = CalculatorStep.RESULT,
                                showConfirmAnimation = true,
                                robotMessage = "Excellent! I've calculated the unit price for you. The result shows the cost per ${state.unit?.displayName}."
                            )
                        )
                    }
                },
                enabled = priceText.toDoubleOrNull()?.let { it > 0 } == true,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RetroColors.Primary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("CALCULATE", style = RetroTypography.labelLarge)
            }
        }
    }
}

@Composable
fun ResultDisplay(
    state: CalculatorState,
    onReset: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1500) // Wait for animation to complete
    }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "CALCULATION RESULT",
            style = RetroTypography.headlineLarge.copy(
                color = RetroColors.Accent,
                fontSize = 14.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = RetroColors.Primary.copy(alpha = 0.2f)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Quantity: ${state.quantity} ${state.unit?.displayName}",
                    style = RetroTypography.bodyMedium.copy(color = Color.White)
                )
                Text(
                    text = "Total Price: ₹${String.format("%.2f", state.totalPrice)}",
                    style = RetroTypography.bodyMedium.copy(color = Color.White)
                )
                
                Divider(color = RetroColors.Accent.copy(alpha = 0.3f))
                
                Text(
                    text = "Unit Price: ₹${String.format("%.2f", state.unitPrice)} per ${state.unit?.displayName}",
                    style = RetroTypography.headlineLarge.copy(
                        color = RetroColors.Accent,
                        fontSize = 16.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        
        Button(
            onClick = onReset,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = RetroColors.Accent,
                contentColor = RetroColors.DarkBlue
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "🔄 NEW CALCULATION",
                style = RetroTypography.labelLarge.copy(
                    color = RetroColors.DarkBlue
                )
            )
        }
    }
}