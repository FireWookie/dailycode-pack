package ru.dailycode.pack.decompose_ext.animations.platform

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import kotlin.math.abs

internal fun Modifier.fadeIos(factor: Float) =
    drawWithContent {
        drawContent()
        val alpha = (abs(factor) * IosAnimationConsts.MAX_OVERLAY_ALPHA)
            .coerceIn(0f, IosAnimationConsts.MAX_OVERLAY_ALPHA)
        drawRect(color = Color(0f, 0f, 0f, alpha))
    }
