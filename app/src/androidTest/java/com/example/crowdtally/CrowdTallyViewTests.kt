package com.example.crowdtally

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.crowdtally.model.Counter
import com.example.crowdtally.screen.CONFIGURE_BUTTON_TAG
import com.example.crowdtally.screen.CountingView
import com.example.crowdtally.screen.CrowdTallyState
import com.example.crowdtally.screen.DECREMENT_BUTTON_TAG
import com.example.crowdtally.screen.INCREMENT_BUTTON_TAG
import com.example.crowdtally.screen.PEPE_IMAGE_TAG
import com.example.crowdtally.screen.toTestTag

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class CrowdTallyViewTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun counting_view_correctly_displays_the_screen_state() {

        val counter = Counter()

        composeTestRule.setContent {
            CountingView(
                state = CrowdTallyState.Counting(counter),
                onIncrement = {},
                onDecrement = {},
                onCrowdTallyIntent = {}
            )
        }

        composeTestRule.onNodeWithTag(testTag = counter.toTestTag()).assertExists()
        composeTestRule.onNodeWithTag(testTag = PEPE_IMAGE_TAG).assertExists()
        composeTestRule.onNodeWithTag(testTag = INCREMENT_BUTTON_TAG).assertExists()
        composeTestRule.onNodeWithTag(testTag = DECREMENT_BUTTON_TAG).assertExists()
        composeTestRule.onNodeWithTag(testTag = CONFIGURE_BUTTON_TAG).assertExists()
    }

}