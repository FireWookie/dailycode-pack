package ru.dailycode.pack.compose_ext.modifiers

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.padding(
    horizontal: Dp? = null,
    vertical: Dp? = null,
    top: Dp = 0.dp,
    bottom: Dp = 0.dp,
    start: Dp = 0.dp,
    end: Dp = 0.dp
) = this.padding(
    start = horizontal ?: start,
    end = horizontal ?: end,
    top = vertical ?: top,
    bottom = vertical ?: bottom
)
