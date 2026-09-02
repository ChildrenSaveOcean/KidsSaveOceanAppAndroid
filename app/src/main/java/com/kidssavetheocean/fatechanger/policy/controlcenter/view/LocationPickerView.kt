package com.kidssavetheocean.fatechanger.policy.controlcenter.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kidssavetheocean.fatechanger.presentation.KstoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPicker(
    locations: List<String>,
    onLocationSelected: (String) -> Unit,
    onChooseLocation: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val locationValue = if (locations.isEmpty()) "" else locations.first()
    var selectedLocation by remember { mutableStateOf(locationValue) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            modifier
                .background(color = Color.White, RectangleShape)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
    ) {
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = selectedLocation,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Location")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded,
                    )
                },
                modifier =
                    Modifier
                        .menuAnchor(
                            type = MenuAnchorType.PrimaryNotEditable,
                            enabled = true,
                        ).fillMaxWidth()
                        .background(Color.White),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
            )

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                locations.forEach { location ->
                    DropdownMenuItem(text = {
                        Text(location)
                    }, onClick = {
                        selectedLocation = location
                        onLocationSelected(location)
                        expanded = false
                    })
                }
            }
        }

        Button(
            onClick = onChooseLocation,
            modifier = Modifier.height(32.dp),
        ) {
            Text("Choose Location")
        }
    }
}

@Preview
@Composable
fun PickerPreview() {
    KstoTheme {
        LocationPicker(
            listOf("asd", "qwewq", "Alaska", "Bob the builder"),
            onLocationSelected = {
            },
            onChooseLocation = { },
            modifier = Modifier,
        )
    }
}
