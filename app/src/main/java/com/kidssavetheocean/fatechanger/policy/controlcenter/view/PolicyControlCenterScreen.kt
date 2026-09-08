package com.kidssavetheocean.fatechanger.policy.controlcenter.view

import android.widget.Spinner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.firebase.repository.HijackPolicy
import com.kidssavetheocean.fatechanger.firebase.repository.HijackPolicyLocation
import com.kidssavetheocean.fatechanger.policy.controlcenter.PolicyControlCenterUiState
import com.kidssavetheocean.fatechanger.policy.controlcenter.viewmodel.ControlCenterUiEvent
import com.kidssavetheocean.fatechanger.presentation.KstoTheme

@Composable
fun PolicyControlCenterScreen(state: PolicyControlCenterUiState, onEvent: (ControlCenterUiEvent) -> Unit) {
    val context = LocalContext.current
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
        if(state.isLoadingData){
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Spinner(context)
            }
        } else{
            PolicyControlCenterView(state, onEvent)

        }
    }
}

val policyTextColor = Color(0xFF53585f)

@Preview
@Composable
fun PolicyPreview() {
    KstoTheme {
        PolicyControlCenterScreen(
            PolicyControlCenterUiState(
                isLoadingData = false,
                selectedPolicy = HijackPolicy(description = "Water for all"),
                policyLocations = listOf(
                    HijackPolicyLocation("asd", "UK"),
                    HijackPolicyLocation("asd", "UK"),
                    HijackPolicyLocation("asd", "UK"),
                ),
            ),

            {},
        )
    }
}
