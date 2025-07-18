package com.foxmobile.foxnote.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Orange,
    onPrimary = Color.White,
    primaryContainer = Orange,
    onPrimaryContainer = Orange,

    secondary = Orange,
    onSecondary = Color.White,
    secondaryContainer = Orange,
    onSecondaryContainer = Orange,

    tertiary = Orange,
    onTertiary = Color.White,
    tertiaryContainer = Orange,
    onTertiaryContainer = Orange,

    background = Black,
    onBackground = Orange,

    surface = Black,
    onSurface = Orange,

    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Black,

    outline = Orange
)

private val LightColorScheme = lightColorScheme(
    primary = Orange,
    onPrimary = Color.White,
    primaryContainer = Orange,
    onPrimaryContainer = Orange,

    secondary = Orange,
    onSecondary = Color.White,
    secondaryContainer = Orange,
    onSecondaryContainer = Orange,

    tertiary = Orange,
    onTertiary = Color.White,
    tertiaryContainer = Orange,
    onTertiaryContainer = Orange,

    background = Black,
    onBackground = Orange,

    surface = Black,
    onSurface = Orange,

    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Orange,

    outline = Orange
)

@Composable
fun FoxNoteTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}