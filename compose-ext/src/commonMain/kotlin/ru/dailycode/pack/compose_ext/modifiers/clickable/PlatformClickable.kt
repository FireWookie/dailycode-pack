package ru.dailycode.pack.compose_ext.modifiers.clickable

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo

fun Modifier.platformClickable(
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    onClick: () -> Unit
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "platformClickable"
        properties["enabled"] = enabled
        properties["onClick"] = onClick
    }
) {
    val localInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    this.clickable(
        enabled = enabled,
        onClick = onClick,
        interactionSource = localInteractionSource,
        indication = platformIndication()
    )
}

expect fun platformIndication(): Indication
