package com.kidssavetheocean.fatechanger.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val policyTextColor = Color(0xFF53585f)
val buttonColor = Color(0xe71fa1e9)
val disabledButtonColor = Color(0xCB6AB9FF)

val lightTheme = lightColorScheme(
    primary = buttonColor,
    outline = buttonColor,
)


@Composable
fun KstoTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
){
    MaterialTheme(
        colorScheme = lightTheme
    ) {
        content()
    }
}


