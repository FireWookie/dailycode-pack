package ru.dailycode.pack.compose_ext.components.input.bottom_sheet.internal

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

@Composable
internal fun BottomSheetScrim(
    isVisible: Boolean,
    enabled: Boolean,
    backProgress: Float?,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
) {
    val animationSpec: AnimationSpec<Color> = tween(
        durationMillis = DailyBottomSheetConstants.SHEET_ANIMATION_DURATION,
        easing = DailyBottomSheetConstants.sheetEasing
    )

    val showedColor = DailyBottomSheetConstants.showedScrimColor
    val hiddenColor = DailyBottomSheetConstants.hiddenScrimColor

    val fallBackAnimation = if (isVisible) showedColor else hiddenColor
    fun animation(backProgress: Float): Color =
        if (isVisible) {
            val bp = backProgress.coerceIn(0f, 1f)
            lerp(
                start = showedColor,
                stop = hiddenColor,
                fraction = bp
            )
        } else {
            Color.Transparent
        }

    val scrimColor =
        when (backProgress) {
            null -> fallBackAnimation
            else -> animation(backProgress)
        }

    val animatedScrimColor by animateColorAsState(
        targetValue = scrimColor,
        animationSpec = animationSpec
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(animatedScrimColor)
            .clickable(
                enabled = isVisible && enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onDismiss
            )
    )
}
