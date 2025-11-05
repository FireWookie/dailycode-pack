package ru.dailycode.pack.compose_ext.modifiers.shadow

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Shape


fun Modifier.dailyShadow(
    config: ShadowConfig,
    shape: Shape
) = this.dropShadow(
    shape = shape,
    shadow = config.toComposeShadow()
)