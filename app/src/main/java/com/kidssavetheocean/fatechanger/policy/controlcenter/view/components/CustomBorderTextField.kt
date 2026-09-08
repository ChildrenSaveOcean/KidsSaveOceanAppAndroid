package com.kidssavetheocean.fatechanger.policy.controlcenter.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidssavetheocean.fatechanger.presentation.KstoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorderTextInput(
    startingInput: String, isEnabled: Boolean = true, onValueChange: (String) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val input = remember { mutableStateOf(startingInput) }

    BasicTextField(
        enabled = isEnabled,
        value = input.value,
        onValueChange = {
            input.value = it
            onValueChange(it)
        },
        modifier = Modifier
            .width(80.dp)
            .height(30.dp),
        textStyle = TextStyle(
            fontSize = 12.sp, color = if (isEnabled) Color.Black else Color.Gray,
        ),
        singleLine = true,
        interactionSource = interactionSource,
        decorationBox = { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = "",
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                container = {
                    OutlinedTextFieldDefaults.Container(
                        enabled = isEnabled,
                        isError = false,
                        interactionSource = interactionSource,
                        colors = OutlinedTextFieldDefaults.colors().copy(
                            disabledContainerColor = Color.White,
                        ),
                        shape = RoundedCornerShape(16.dp),
                        focusedBorderThickness = 2.dp,
                        unfocusedBorderThickness = 2.dp,
                    )
                },
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    )
}

@Preview
@Composable
fun PreviewTextInput() {
    KstoTheme() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            BorderTextInput("0", false, {})
        }
    }
}
