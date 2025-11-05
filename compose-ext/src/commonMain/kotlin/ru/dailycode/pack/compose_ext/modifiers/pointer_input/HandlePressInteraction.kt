
package ru.dailycode.pack.compose_ext.modifiers.pointer_input

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.coroutines.cancellation.CancellationException

internal fun Modifier.handlePressInteraction(
    enabled: Boolean,
    isPressed: (Boolean) -> Unit,
    onClick: () -> Unit
): Modifier = if (enabled) {
    this.pointerInput(Unit) {
        detectTapGestures(
            onPress = {
                isPressed(true)
                try {
                    awaitRelease()
                    onClick.invoke()
                } catch (_: CancellationException) {
                    isPressed(false)
                } finally {
                    isPressed(false)
                }
            }
        )
    }
} else {
    Modifier
}
