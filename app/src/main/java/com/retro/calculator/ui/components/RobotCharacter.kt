package com.retro.calculator.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.retro.calculator.ui.theme.RetroColors
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RobotCharacter(
    modifier: Modifier = Modifier,
    isExcited: Boolean = false,
    showConfirmAnimation: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label = "robot_animations")
    
    // Eye scanning animation
    val eyeColor by infiniteTransition.animateColor(
        initialValue = RetroColors.Primary,
        targetValue = RetroColors.Green,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "eye_scan"
    )
    
    // Antenna pulse
    val antennaPulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "antenna_pulse"
    )
    
    // Arm wave animation
    val armWave by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (isExcited) 30f else 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "arm_wave"
    )
    
    // Chest glow
    val chestGlow by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "chest_glow"
    )
    
    // Confirm animation (360° rotation)
    var confirmRotation by remember { mutableFloatStateOf(0f) }
    
    LaunchedEffect(showConfirmAnimation) {
        if (showConfirmAnimation) {
            animate(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = tween(1500, easing = EaseInOut)
            ) { value, _ ->
                confirmRotation = value
            }
        }
    }
    
    Box(
        modifier = modifier
            .size(160.dp)
            .rotate(if (showConfirmAnimation) confirmRotation else 0f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawRobot(
                drawScope = this,
                eyeColor = eyeColor,
                antennaPulse = antennaPulse,
                armWave = armWave,
                chestGlow = chestGlow,
                isExcited = isExcited
            )
        }
    }
}

private fun drawRobot(
    drawScope: DrawScope,
    eyeColor: Color,
    antennaPulse: Float,
    armWave: Float,
    chestGlow: Float,
    isExcited: Boolean
) {
    val centerX = drawScope.size.width / 2
    val centerY = drawScope.size.height / 2
    val scale = drawScope.size.width / 160f
    
    // Robot Head
    drawScope.drawRoundRect(
        color = RetroColors.Primary,
        topLeft = androidx.compose.ui.geometry.Offset(
            centerX - 64 * scale,
            centerY - 80 * scale
        ),
        size = androidx.compose.ui.geometry.Size(128 * scale, 96 * scale),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(24 * scale, 24 * scale),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3 * scale)
    )
    
    drawScope.drawRoundRect(
        brush = Brush.verticalGradient(
            colors = listOf(RetroColors.Primary, RetroColors.BgCard, RetroColors.DarkBlue)
        ),
        topLeft = androidx.compose.ui.geometry.Offset(
            centerX - 64 * scale,
            centerY - 80 * scale
        ),
        size = androidx.compose.ui.geometry.Size(128 * scale, 96 * scale),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(24 * scale, 24 * scale)
    )
    
    // Eyes
    drawScope.drawCircle(
        color = eyeColor,
        radius = 12 * scale,
        center = androidx.compose.ui.geometry.Offset(
            centerX - 20 * scale,
            centerY - 60 * scale
        )
    )
    
    drawScope.drawCircle(
        color = eyeColor,
        radius = 12 * scale,
        center = androidx.compose.ui.geometry.Offset(
            centerX + 20 * scale,
            centerY - 60 * scale
        )
    )
    
    // Antennas
    drawAntennas(drawScope, centerX, centerY - 80 * scale, scale, antennaPulse)
    
    // Robot Body
    drawScope.drawRoundRect(
        brush = Brush.verticalGradient(
            colors = listOf(RetroColors.BgCard, RetroColors.Primary, RetroColors.DarkBlue)
        ),
        topLeft = androidx.compose.ui.geometry.Offset(
            centerX - 72 * scale,
            centerY - 16 * scale
        ),
        size = androidx.compose.ui.geometry.Size(144 * scale, 128 * scale),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(16 * scale, 16 * scale)
    )
    
    // Chest Panel
    drawScope.drawRoundRect(
        color = RetroColors.DarkBlue,
        topLeft = androidx.compose.ui.geometry.Offset(
            centerX - 32 * scale,
            centerY + 8 * scale
        ),
        size = androidx.compose.ui.geometry.Size(64 * scale, 64 * scale),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(12 * scale, 12 * scale)
    )
    
    // Calculator icon in chest
    drawScope.drawRect(
        color = RetroColors.Accent.copy(alpha = chestGlow),
        topLeft = androidx.compose.ui.geometry.Offset(
            centerX - 16 * scale,
            centerY + 24 * scale
        ),
        size = androidx.compose.ui.geometry.Size(32 * scale, 32 * scale)
    )
    
    // Arms with wave animation
    drawScope.rotate(armWave, pivot = androidx.compose.ui.geometry.Offset(centerX - 72 * scale, centerY + 24 * scale)) {
        drawScope.drawRoundRect(
            brush = Brush.verticalGradient(
                colors = listOf(RetroColors.Primary, RetroColors.DarkBlue)
            ),
            topLeft = androidx.compose.ui.geometry.Offset(
                centerX - 96 * scale,
                centerY + 8 * scale
            ),
            size = androidx.compose.ui.geometry.Size(16 * scale, 64 * scale),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(8 * scale, 8 * scale)
        )
    }
    
    drawScope.rotate(-armWave, pivot = androidx.compose.ui.geometry.Offset(centerX + 72 * scale, centerY + 24 * scale)) {
        drawScope.drawRoundRect(
            brush = Brush.verticalGradient(
                colors = listOf(RetroColors.Primary, RetroColors.DarkBlue)
            ),
            topLeft = androidx.compose.ui.geometry.Offset(
                centerX + 80 * scale,
                centerY + 8 * scale
            ),
            size = androidx.compose.ui.geometry.Size(16 * scale, 64 * scale),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(8 * scale, 8 * scale)
        )
    }
    
    // Robot Base
    drawScope.drawRoundRect(
        brush = Brush.verticalGradient(
            colors = listOf(RetroColors.Primary, RetroColors.DarkBlue)
        ),
        topLeft = androidx.compose.ui.geometry.Offset(
            centerX - 60 * scale,
            centerY + 112 * scale
        ),
        size = androidx.compose.ui.geometry.Size(120 * scale, 32 * scale),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(16 * scale, 16 * scale)
    )
}

private fun drawAntennas(
    drawScope: DrawScope,
    centerX: Float,
    topY: Float,
    scale: Float,
    pulse: Float
) {
    val antennaPositions = listOf(-16f, 0f, 16f)
    val antennaColors = listOf(RetroColors.Accent, RetroColors.Green, RetroColors.Red)
    
    antennaPositions.forEachIndexed { index, xOffset ->
        // Antenna stem
        drawScope.drawLine(
            color = RetroColors.Primary,
            start = androidx.compose.ui.geometry.Offset(
                centerX + xOffset * scale,
                topY - 48 * scale
            ),
            end = androidx.compose.ui.geometry.Offset(
                centerX + xOffset * scale,
                topY - 8 * scale
            ),
            strokeWidth = 3 * scale
        )
        
        // Antenna tip
        drawScope.drawCircle(
            color = antennaColors[index],
            radius = 6 * scale * pulse,
            center = androidx.compose.ui.geometry.Offset(
                centerX + xOffset * scale,
                topY - 48 * scale
            )
        )
    }
}