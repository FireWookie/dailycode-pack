package ru.dailycode.pack.compose_ext.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import ru.dailycode.pack.compose_ext.modifiers.border.BorderConfig
import ru.dailycode.pack.compose_ext.modifiers.border.BorderSide
import ru.dailycode.pack.compose_ext.modifiers.border.edgeBorder
import ru.dailycode.pack.compose_ext.modifiers.clickable.platformClickable

public fun Modifier.interactiveElement(
    borderConfig: BorderConfig? = null,
    backgroundColor: Color? = null,
    shape: Shape = RectangleShape,
    enabled: Boolean = true,
    contentColor: Color? = null,
    onClick: (() -> Unit)? = null
): Modifier = this
    .clip(shape)
    .thenIf(onClick) { onClick ->
        platformClickable(
            enabled = enabled,
            onClick = onClick
        )
    }
    .thenIf(backgroundColor) { background(it, shape) }
    .thenIf(borderConfig) { config ->
        if (config.sides == BorderSide.entries.toSet()) {
            border(config.width, config.color, shape)
        } else {
            edgeBorder(config = config, shape)
        }
    }
