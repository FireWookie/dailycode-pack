package ru.dailycode.pack.decompose_ext.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
internal fun <SE : Any> CollectSideEffects(
    sideEffects: Flow<SE>,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    onSideEffect: (suspend (sideEffect: SE) -> Unit)
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val callback by rememberUpdatedState(newValue = onSideEffect)

    LaunchedEffect(sideEffects, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            sideEffects.collect { callback(it) }
        }
    }
}