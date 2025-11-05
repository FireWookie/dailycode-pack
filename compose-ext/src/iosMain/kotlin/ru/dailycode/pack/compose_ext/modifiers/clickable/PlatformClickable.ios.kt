package ru.dailycode.pack.compose_ext.modifiers.clickable

import androidx.compose.foundation.Indication
import ru.dailycode.pack.compose_ext.modifiers.clickable.ripple.opacityRipple

actual fun platformIndication(): Indication =
    opacityRipple(0, 0, minAlpha = 0.4f)

