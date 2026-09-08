package com.kidssavetheocean.fatechanger.policy.controlcenter.view

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kidssavetheocean.fatechanger.Constants
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.WebViewActivity
import com.kidssavetheocean.fatechanger.firebase.repository.HijackPolicyLocation
import com.kidssavetheocean.fatechanger.policy.controlcenter.PolicyControlCenterUiState
import com.kidssavetheocean.fatechanger.policy.controlcenter.viewmodel.ControlCenterUiEvent
import com.kidssavetheocean.fatechanger.presentation.KstoTheme

@Composable
fun PolicyControlCenterView(state: PolicyControlCenterUiState, onEvent: (ControlCenterUiEvent) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (state.isLoadingPolicyData && state.isLoadingLocationsData) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return
        }
        if (state.isLoadingPolicyData) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            PolicySelectionSection(state)
        }

        if (!state.isLoadingLocationsData) {
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
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        if (state.selectedLocation != null && state.selectedPolicy != null) {
            SignaturesView(
                plannedSignatures = state.plannedSignatures,
                onUpdatePlannedSignatures = {
                    onEvent(ControlCenterUiEvent.PlannedSignaturesUpdated(it))
                },
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun PolicySelectionSection(state: PolicyControlCenterUiState) {
    val context = LocalContext.current

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
}

@Preview
@Composable
fun CenterViewPreview() {
    KstoTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
        ) {
            PolicyControlCenterView(
                PolicyControlCenterUiState(
                    policyLocations = listOf(
                        HijackPolicyLocation(
                            "asd",
                            "USA - Alaska",
                        ),
                        HijackPolicyLocation(
                            "asd",
                            "USA - California",
                        ),
                        HijackPolicyLocation(
                            "asd",
                            "USA - Arizona",
                        ),
                    ),
                ),
                {},
            )
        }
    }
}
