package ru.dailycode.pack.compose_ext.modifiers.shadow

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset

data class ShadowConfig(
    val radius: Dp,
    val spread: Dp,
    val color: Color,
    val x: Dp,
    val y: Dp,
)

fun ShadowConfig.toComposeShadow(): Shadow = Shadow(
    radius = radius,
    color = color,
    spread = spread,
    offset = DpOffset(x, y),
)
