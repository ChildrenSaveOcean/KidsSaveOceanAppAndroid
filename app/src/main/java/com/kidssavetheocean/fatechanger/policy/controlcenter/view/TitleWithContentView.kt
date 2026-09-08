package com.kidssavetheocean.fatechanger.policy.controlcenter.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidssavetheocean.fatechanger.R

@Composable
fun TitleWithContent(
    showContent: Boolean = false,
    title: Int,
    content: String,
    titleColor: Color = Color.Gray,
    contentColor: Color = Color.Gray,
    shouldBoldContent: Boolean = false
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
    ) {
        Text(
            text = stringResource(title), color = titleColor, fontSize = 14.sp
        )
        if (showContent) {
            Text(
                text = content,
                fontSize = 14.sp,
                fontWeight = if (shouldBoldContent) FontWeight.Bold else null,
                color = contentColor
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    MaterialTheme() {
        TitleWithContent(true,R.string.policy_location_campaigns, "non yet - still voting")
    }
}