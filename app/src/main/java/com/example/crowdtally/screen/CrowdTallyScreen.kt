package com.example.crowdtally.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.crowdtally.model.Counter
import kotlin.math.min

@Composable
fun CrowdTallyScreen(initialState: CrowdTallyState = CrowdTallyState.Configuring(Counter(), 20)) {

    var state: CrowdTallyState by rememberSaveable(saver = CrowdTallyState.Saver)  {
        mutableStateOf(initialState)
    }

    when (val currentState = state) {

        is CrowdTallyState.Counting -> {
            CountingView(
                state = currentState,
                onCrowdTallyIntent = { state = CrowdTallyState.Configuring(counter = currentState.counter, newMax = currentState.counter.max) },
                onIncrement = { state = CrowdTallyState.Counting(counter = currentState.counter.inc()) },
                onDecrement = { state = CrowdTallyState.Counting(counter = currentState.counter.dec()) }
            )
        }

        is CrowdTallyState.Configuring -> {
            ConfiguringView(
                state = currentState,
                onCrowdTallyIntent = { state = CrowdTallyState.Counting(counter = currentState.counter.copy(current = min(currentState.counter.current, currentState.newMax), max = currentState.newMax)) },
                onValueChange = { s ->
                    state = CrowdTallyState.Configuring(

                        counter = currentState.counter,
                        newMax = try {
                            if (s.toInt() < 0) 0 else s.toInt()
                        } catch (e: Exception) {
                            0
                        }

                    )
                }
            )
        }

    }

}

sealed interface CrowdTallyState {

    data class Counting(val counter: Counter) : CrowdTallyState
    data class Configuring(val counter: Counter, val newMax: Int) : CrowdTallyState

    companion object {

        val Saver = Saver<MutableState<CrowdTallyState>, List<Int>>(

            save = { toSave ->
                toSave.value.let { state ->

                    when (state) {
                        is Counting -> listOf(state.counter.current, state.counter.max)
                        is Configuring -> listOf(state.counter.current, state.counter.max, state.newMax)
                    }

                }
            },

            restore = { saved ->

                if (saved.size == 2)
                    mutableStateOf(Counting(Counter(current = saved[0], max = saved[1])))
                else
                    mutableStateOf(Configuring(Counter(current = saved[0], max = saved[1]), newMax = saved[2]))

            }

        )

    }

}