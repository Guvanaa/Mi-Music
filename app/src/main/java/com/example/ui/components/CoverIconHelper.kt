package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector

fun getCoverIconVector(iconName: String): ImageVector {
    return when (iconName) {
        "Sparkles" -> Icons.Default.AutoAwesome
        "Zap" -> Icons.Default.Bolt
        "Sun" -> Icons.Default.WbSunny
        "Disc" -> Icons.Default.Album
        "Flame" -> Icons.Default.LocalFireDepartment
        "Radio" -> Icons.Default.Radio
        else -> Icons.Default.Album
    }
}
