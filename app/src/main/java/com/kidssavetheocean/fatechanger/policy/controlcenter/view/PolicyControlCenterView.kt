package com.kidssavetheocean.fatechanger.policy.controlcenter.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
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

@Composable
fun PolicyControlCenterView(
    showPolicy: Boolean = false
) {
    MaterialTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(R.drawable.policy_vote_top),
                contentDescription = "background Image",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = stringResource(R.string.policy_control_center_title),
                fontWeight = FontWeight.Bold,
                color = policyTextColor
            )

            Text(
                text = stringResource(R.string.policy_control_center_subtitle),
                color = policyTextColor,
                fontSize = 12.sp
            )

            TitleWithContent(
                true,
                R.string.policy_chosen,
                "non yet - still voting",
                policyTextColor,
                policyTextColor,
                true
            )

            TitleWithContent(
                true,
                R.string.policy_location_campaigns,
                "none - still building",
                Color.Red,
                Color.Red,
                false
            )

        }

    }
}

val policyTextColor = Color(0xFF53585f)

@Preview
@Composable
fun PolicyPreview() {
    PolicyControlCenterView(false)
}