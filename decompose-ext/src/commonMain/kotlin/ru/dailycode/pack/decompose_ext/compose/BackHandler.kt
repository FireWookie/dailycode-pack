package ru.dailycode.pack.decompose_ext.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.backhandler.BackEvent
import com.arkivanov.essenty.backhandler.BackHandler

@Composable
public fun BackHandler(
    backHandler: BackHandler,
    isEnabled: Boolean = true,
    callBackProgress: (BackEvent) -> Unit = {},
    onBack: () -> Unit,
) {
    val currentOnBack by rememberUpdatedState(onBack)

    val callback =
        remember {
            BackCallback(
                isEnabled = isEnabled,
                priority = Int.MAX_VALUE,
                onBackProgressed = callBackProgress
            ) {
                currentOnBack()
            }
        }

    SideEffect { callback.isEnabled = isEnabled }

    DisposableEffect(backHandler) {
        backHandler.register(callback)
        onDispose { backHandler.unregister(callback) }
    }
}
