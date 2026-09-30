package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val MiOrange = Color(0xFFFF6700)
val MiOrangeDark = Color(0xFFEA580C)
val Slate950 = Color(0xFF030712)
val Slate900 = Color(0xFF0F172A)
val Slate800 = Color(0xFF1E293B)
val Slate700 = Color(0xFF334155)
val Slate600 = Color(0xFF475569)
val Slate500 = Color(0xFF64748B)
val Slate400 = Color(0xFF94A3B8)
val Slate300 = Color(0xFFCBD5E1)
val Slate200 = Color(0xFFE2E8F0)
val Slate100 = Color(0xFFF1F5F9)
val Emerald500 = Color(0xFF10B981)
val Emerald400 = Color(0xFF34D399)
val Rose500 = Color(0xFFF43F5E)
val Rose400 = Color(0xFFFB7185)
val Purple400 = Color(0xFFC084FC)
val Purple900 = Color(0xFF581C87)

private val MiMusicColorScheme = darkColorScheme(
    primary = MiOrange,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF3B1A06),
    onPrimaryContainer = Color(0xFFFFDBC8),
    secondary = Emerald500,
    onSecondary = Color.Black,
    tertiary = Purple400,
    background = Slate950,
    onBackground = Slate100,
    surface = Slate900,
    onSurface = Slate100,
    surfaceVariant = Slate800,
    onSurfaceVariant = Slate300,
    outline = Slate700,
    error = Rose500
)

val MiMusicTypography = Typography(
    headlineSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = (-0.3).sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp
    )
)

@Composable
fun MiMusicTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MiMusicColorScheme,
        typography = MiMusicTypography,
        content = content
    )
}
