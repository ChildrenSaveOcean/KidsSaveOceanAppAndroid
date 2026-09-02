package com.kidssavetheocean.fatechanger.policy.controlcenter.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidssavetheocean.fatechanger.policy.controlcenter.view.components.BorderTextInput
import com.kidssavetheocean.fatechanger.presentation.KstoTheme
import com.kidssavetheocean.fatechanger.presentation.disabledButtonColor

private val textFontSize = 14.sp
private val textWidth = 148.dp
private val buttonWidth = 100.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignaturesView() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
    ) {
        Text(
            "Your location is currently not live, but you can already fill out your planned signatures below",
            modifier = Modifier.fillMaxWidth(),
            fontSize = textFontSize,
            color = Color.Gray,
            textAlign = TextAlign.Center,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Planned Signatures:",
                modifier = Modifier.width(textWidth),
                fontSize = textFontSize,
            )
            BorderTextInput("")
            Button(
                onClick = {},
                modifier =
                    Modifier
                        .width(buttonWidth)
                        .padding(start = 4.dp),
            ) {
                Text("Update", fontSize = textFontSize)
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Collected Signatures:",
                modifier = Modifier.width(textWidth),
                fontSize = textFontSize,
            )
            BorderTextInput("", false)

            Button(
                enabled = false,
                colors =
                    ButtonDefaults.buttonColors().copy(
                        disabledContainerColor = disabledButtonColor,
                        disabledContentColor = Color.White,
                    ),
                onClick = {},
                modifier =
                    Modifier
                        .width(buttonWidth)
                        .padding(start = 4.dp),
            ) {
                Text("Add", fontSize = textFontSize)
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Your total collected:",
                modifier = Modifier.width(textWidth),
                fontSize = textFontSize,
            )
            BorderTextInput("", false)
        }
    }
}

@Preview
@Composable
fun SignaturesPreview() {
    KstoTheme {
        Box(Modifier.background(Color.White)) {
            SignaturesView()
        }
    }
}
