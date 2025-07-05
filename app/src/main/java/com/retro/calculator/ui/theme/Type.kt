package com.retro.calculator.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.retro.calculator.R

val OrbitronFamily = FontFamily(
    Font(R.font.orbitron_regular, FontWeight.Normal),
    Font(R.font.orbitron_bold, FontWeight.Bold),
    Font(R.font.orbitron_black, FontWeight.Black)
)

val RussoOneFamily = FontFamily(
    Font(R.font.russo_one_regular, FontWeight.Normal)
)

val ShareTechMonoFamily = FontFamily(
    Font(R.font.share_tech_mono_regular, FontWeight.Normal)
)

val RetroTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = OrbitronFamily,
        fontWeight = FontWeight.Black,
        fontSize = 32.sp,
        letterSpacing = 3.2.sp
    ),
    displayMedium = TextStyle(
        fontFamily = RussoOneFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 2.4.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = OrbitronFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 2.0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = ShareTechMonoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 1.6.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = ShareTechMonoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 1.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = ShareTechMonoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 1.2.sp
    )
)