package com.kidssavetheocean.fatechanger.policy.controlcenter.view

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kidssavetheocean.fatechanger.policy.controlcenter.view.components.BorderTextInput
import com.kidssavetheocean.fatechanger.presentation.KstoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignaturesView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Planned Signatures:", modifier = Modifier.width(164.dp))
            BorderTextInput("")
            Button(
                onClick = {}, modifier = Modifier
                    .width(100.dp)
                    .padding(start = 4.dp)) {
                Text("Update")
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Collected Signatures:", modifier = Modifier.width(164.dp))
            val interactionSource = remember { MutableInteractionSource() }
            BorderTextInput("")

            Button(
                onClick = {}, modifier = Modifier
                    .width(100.dp)
                    .padding(start = 4.dp)) {
                Text("Add")
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Your total collected:", modifier = Modifier.width(164.dp))
            val interactionSource = remember { MutableInteractionSource() }
            BorderTextInput("")

        }


    }
}

@Preview
@Composable
fun SignaturesPreview() {
    KstoTheme() {
        Box(Modifier.background(Color.White)) {
            SignaturesView()

        }
    }
}