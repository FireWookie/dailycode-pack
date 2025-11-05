package ru.dailycode.pack.compose_ext.modifiers

import androidx.compose.ui.Modifier

inline fun <T> Modifier.thenIf(
    value: T?,
    predicate: (T?) -> Boolean = { it != null },
    crossinline action: Modifier.(T) -> Modifier
): Modifier = if (value != null && predicate(value)) then(Modifier.action(value)) else this

inline fun Modifier.thenIf(
    value: Boolean,
    crossinline action: Modifier.(Boolean) -> Modifier
): Modifier = if (value) then(Modifier.action(value)) else this
