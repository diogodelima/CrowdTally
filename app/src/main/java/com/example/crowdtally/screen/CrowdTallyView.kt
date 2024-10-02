package com.example.crowdtally.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.example.crowdtally.R
import com.example.crowdtally.model.Counter

fun Counter.toTestTag() = "counter_$current"

const val INCREMENT_BUTTON_TAG = "increment_button"
const val DECREMENT_BUTTON_TAG = "decrement_button"
const val PEPE_IMAGE_TAG = "pepe_image_tag"
const val CONFIGURE_BUTTON_TAG = "configure_button_tag"

@Composable
fun CountingView(
    modifier: Modifier = Modifier,
    state: CrowdTallyState.Counting,
    onCrowdTallyIntent: (Counter) -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {

    val counter = state.counter

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier.fillMaxSize(0.5f).testTag(PEPE_IMAGE_TAG),
                painter = painterResource(R.drawable.pepe),
                contentDescription = null
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                CustomButton(
                    modifier = Modifier.testTag(INCREMENT_BUTTON_TAG),
                    onClick = onIncrement,
                    text = stringResource(R.string.counter_increase_text),
                    enabled = counter.canIncrease()
                )

                Text(
                    modifier = Modifier.testTag(counter.toTestTag()),
                    text = counter.current.toString(),
                    fontSize = TextUnit(14f, TextUnitType.Em)
                )

                CustomButton(
                    modifier = Modifier.testTag(DECREMENT_BUTTON_TAG),
                    onClick = onDecrement,
                    text = stringResource(R.string.counter_decrease_text),
                    enabled = counter.canDecrease()
                )

            }

            CustomButton(
                modifier = Modifier.testTag(CONFIGURE_BUTTON_TAG),
                onClick = { onCrowdTallyIntent(counter) },
                text = stringResource(R.string.configure_text),
                enabled = true
            )

        }

    }

}

@Composable
fun ConfiguringView(
    state: CrowdTallyState.Configuring,
    onCrowdTallyIntent: () -> Unit,
    onValueChange: (String) -> Unit,
) {

    val value = state.newMax

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier.fillMaxSize(0.5f),
                painter = painterResource(R.drawable.pepe),
                contentDescription = null
            )

            TextField(
                value = value.toString(),
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    fontSize = TextUnit(6f, TextUnitType.Em),
                    textAlign = TextAlign.Center
                )
            )

            CustomButton(
                onClick = onCrowdTallyIntent,
                text = stringResource(R.string.configure_save),
                enabled = true
            )

        }

    }

}

@Composable
private fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    fontSize: TextUnit = TextUnit(5f, TextUnitType.Em),
    enabled: Boolean = false
) {

    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled
    ) {

        Text(
            text = text,
            fontSize = fontSize
        )

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CountingViewPreview() {
    CountingView(state = CrowdTallyState.Counting(counter = Counter()), onCrowdTallyIntent = {}, onIncrement = {}, onDecrement = {})
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ConfiguringViewPreview() {
    ConfiguringView(state = CrowdTallyState.Configuring(counter = Counter(), newMax = 20), onCrowdTallyIntent = {}, onValueChange = {})
}