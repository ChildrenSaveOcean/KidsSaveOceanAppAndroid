package com.kidssavetheocean.fatechanger.policy.controlcenter.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.presentation.KstoTheme

@Composable
fun PolicyControlCenterView(showPolicy: Boolean = false) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
    ) {
        Image(
            painter = painterResource(R.drawable.policy_vote_top),
            contentDescription = "background Image",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
        )

        Text(
            text = stringResource(R.string.policy_control_center_title),
            fontWeight = FontWeight.Bold,
            color = policyTextColor,
        )

        Text(
            text = stringResource(R.string.policy_control_center_subtitle),
            color = policyTextColor,
            fontSize = 12.sp,
        )

        TitleWithContent(
            true,
            R.string.policy_chosen,
            "non yet - still voting",
            policyTextColor,
            policyTextColor,
            true,
        )

        TitleWithContent(
            true,
            R.string.policy_location_campaigns,
            "none - still building",
            Color.Red,
            Color.Red,
            false,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(
                onClick = {},
            ) {
                Text("Learn More")
            }

            Button(
                onClick = {},
            ) {
                Text("Share")
            }
        }
        LocationPicker(
            listOf(
            "USA - Alaska",
            "UK",
            "SANTA",
            "TEST",
            "USA - Alaska",
            "UK",
            "SANTA",
            "TEST",
            "USA - Alaska",
            "UK",
            "SANTA",
            "TEST"
        ), onLocationSelected = {}, onChooseLocation = {})

        SignaturesView()
    }
}

val policyTextColor = Color(0xFF53585f)

@Preview
@Composable
fun PolicyPreview() {
    KstoTheme {
        PolicyControlCenterView(false)
    }
}
