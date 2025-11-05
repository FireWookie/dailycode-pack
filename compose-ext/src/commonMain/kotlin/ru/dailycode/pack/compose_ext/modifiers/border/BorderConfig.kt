package ru.dailycode.pack.compose_ext.modifiers.border

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

data class BorderConfig(
    val width: Dp,
    val color: Color,
    val sides: Set<BorderSide> = BorderSide.entries.toSet(),
)

enum class BorderSide {
    TOP,
    LEFT,
    BOTTOM,
    RIGHT,
}
