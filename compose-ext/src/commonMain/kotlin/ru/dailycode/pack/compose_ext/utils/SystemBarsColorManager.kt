package ru.dailycode.pack.compose_ext.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

public object SystemBarsColorManager {
    private val _isDark = MutableSharedFlow<Boolean>(replay = 1)
    internal val isDark = _isDark.asSharedFlow()

    private val stack = ArrayDeque<Boolean>()

    internal suspend fun push(dark: Boolean) {
        _isDark.replayCache.firstOrNull()?.let(stack::addLast)
        _isDark.emit(dark)
    }

    internal suspend fun pop() {
        stack.removeLastOrNull()?.also { _isDark.emit(it) }
    }

    @Composable
    public fun ProvideIsDark(dark: Boolean) {
        LaunchedEffect(dark) {
            push(dark)
            try {
                awaitCancellation()
            } finally {
                pop()
            }
        }
    }
}
