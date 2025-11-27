package ru.dailycode.pack.decompose_ext.animations.animations.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

fun Modifier.offsetXFactor(factor: Float): Modifier =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        layout(placeable.width, placeable.height) {
            val offsetX = (placeable.width.toFloat() * factor).toInt()
            placeable.placeRelative(x = offsetX, y = 0)
        }
    }
