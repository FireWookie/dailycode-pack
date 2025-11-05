package ru.dailycode.pack.compose_ext.modifiers.pointer_input

import androidx.compose.ui.Modifier

public fun Modifier.noIndicationClickable(
    enabled: Boolean = true,
    isPressed: (Boolean) -> Unit = {},
    onClick: () -> Unit
): Modifier = handlePressInteraction(enabled, isPressed, onClick)