package com.kidssavetheocean.fatechanger.policy.controlcenter.view

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kidssavetheocean.fatechanger.Constants
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.WebViewActivity
import com.kidssavetheocean.fatechanger.policy.controlcenter.PolicyControlCenterUiState
import com.kidssavetheocean.fatechanger.policy.controlcenter.viewmodel.ControlCenterUiEvent

@Composable
fun PolicyControlCenterView(state: PolicyControlCenterUiState, onEvent: (ControlCenterUiEvent) -> Unit) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TitleWithContent(
            true,
            R.string.policy_chosen,
            state.selectedPolicy?.description ?: "non yet - still voting",
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
                onClick = {
                    val intent = Intent(context, WebViewActivity::class.java)
                    intent.putExtra(Constants.INTENT_URL, Constants.URL_POLICY_VIDEO)
                    context.startActivity(intent)
                },
            ) {
                Text("Learn More")
            }

            Button(
                onClick = {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, Constants.URL_SHARE_TEXT)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                },
            ) {
                Text("Share")
            }
        }

        LocationPicker(
            locations = state.policyLocations.map { it.location },
            onChooseLocation = { index ->
                val model = state.policyLocations.getOrNull(index)
                model?.let {
                    onEvent(ControlCenterUiEvent.LocationChosen(model))
                }
            },
            presetLocation = state.selectedLocation?.location,
        )
        if (state.selectedLocation != null) {
            SignaturesView {
                onEvent(ControlCenterUiEvent.PlannedSignaturesUpdated(it))
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
