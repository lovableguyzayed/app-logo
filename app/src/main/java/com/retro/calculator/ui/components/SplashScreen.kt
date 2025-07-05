package com.retro.calculator.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.retro.calculator.ui.theme.RetroColors
import com.retro.calculator.ui.theme.RetroTypography
import com.retro.calculator.utils.HapticFeedback
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onEnterApp: () -> Unit) {
    val context = LocalContext.current
    val hapticFeedback = remember { HapticFeedback(context) }
    
    // Animation states
    val infiniteTransition = rememberInfiniteTransition(label = "splash_animations")
    
    val robotFloat by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -8f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "robot_float"
    )
    
    val logoGlow by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_glow"
    )
    
    val buttonPulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "button_pulse"
    )
    
    Box(
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
        // Background grid pattern
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawGridPattern(this)
        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Robot Character
            Box(
                modifier = Modifier
                    .offset(y = robotFloat.dp)
                    .padding(bottom = 32.dp)
            ) {
                RobotCharacter()
            }
            
            // Logo Text
            Text(
                text = "QUANTITY PRICE",
                style = RetroTypography.displayLarge.copy(
                    color = RetroColors.Accent.copy(alpha = logoGlow),
                    fontSize = 24.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = "CALCULATOR",
                style = RetroTypography.displayMedium.copy(
                    color = RetroColors.Primary.copy(alpha = logoGlow),
                    fontSize = 20.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            // Subtitle
            Text(
                text = "Calculate precise unit prices and quantities with our advanced retro calculator assistant",
                style = RetroTypography.bodyMedium.copy(
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
            )
            
            // Enter Button
            Button(
                onClick = {
                    hapticFeedback.performHapticFeedback()
                    onEnterApp()
                },
                modifier = Modifier
                    .scale(buttonPulse)
                    .padding(vertical = 32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RetroColors.Primary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "🚀 ENTER CALCULATOR",
                    style = RetroTypography.labelLarge.copy(
                        fontSize = 14.sp,
                        letterSpacing = 2.sp
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            
            // Version Info
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 32.dp)
            ) {
                Text(
                    text = "RETRO-BOT CALCULATOR v2.0",
                    style = RetroTypography.labelLarge.copy(
                        color = RetroColors.Primary.copy(alpha = 0.8f),
                        fontSize = 10.sp
                    )
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    StatusLight(RetroColors.Green)
                    StatusLight(RetroColors.Accent)
                    StatusLight(RetroColors.Red)
                }
            }
        }
        
        // Decorative elements
        DecorativeElements()
    }
}

@Composable
private fun StatusLight(color: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "status_light")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "status_alpha"
    )
    
    Box(
        modifier = Modifier
            .size(6.dp)
            .background(
                color = color.copy(alpha = alpha),
                shape = RoundedCornerShape(50%)
            )
    )
}

@Composable
private fun DecorativeElements() {
    val infiniteTransition = rememberInfiniteTransition(label = "decorative")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "decorative_pulse"
    )
    
    Box(
        modifier = Modifier
            .offset(x = 24.dp, y = 32.dp)
            .size(12.dp)
            .background(
                color = RetroColors.Accent.copy(alpha = pulse * 0.4f),
                shape = RoundedCornerShape(50%)
            )
    )
    
    Box(
        modifier = Modifier
            .offset(x = (-24).dp, y = 64.dp)
            .size(8.dp)
            .background(
                color = RetroColors.Primary.copy(alpha = pulse * 0.4f),
                shape = RoundedCornerShape(50%)
            )
    )
}

private fun drawGridPattern(drawScope: DrawScope) {
    val gridSize = 30.dp.toPx()
    val strokeWidth = 1.dp.toPx()
    val color = Color(0xFF6B88D3).copy(alpha = 0.05f)
    
    // Draw vertical lines
    var x = 0f
    while (x <= drawScope.size.width) {
        drawScope.drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(x, 0f),
            end = androidx.compose.ui.geometry.Offset(x, drawScope.size.height),
            strokeWidth = strokeWidth
        )
        x += gridSize
    }
    
    // Draw horizontal lines
    var y = 0f
    while (y <= drawScope.size.height) {
        drawScope.drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(0f, y),
            end = androidx.compose.ui.geometry.Offset(drawScope.size.width, y),
            strokeWidth = strokeWidth
        )
        y += gridSize
    }
}