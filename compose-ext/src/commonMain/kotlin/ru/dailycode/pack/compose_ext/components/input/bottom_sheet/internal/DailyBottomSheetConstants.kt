package ru.dailycode.pack.compose_ext.components.input.bottom_sheet.internal

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp

internal object DailyBottomSheetConstants {
    const val SHEET_ANIMATION_DURATION = 500

    val sheetEasing = CubicBezierEasing(0.32f, 0.72f, 0f, 1f)

    val showedScrimColor = androidx.compose.ui.graphics.Color(0x59000000)
    val hiddenScrimColor = androidx.compose.ui.graphics.Color(0x00000000)

    private const val SHAPE_FRACTION_MODIFIER = 0.5F
    private val minCornerSize = 4.dp
    val predictiveBackMaxDistanceContainer = 120.dp

    val enterAnimation = slideInVertically(
        animationSpec = tween(durationMillis = SHEET_ANIMATION_DURATION, easing = sheetEasing)
    ) { height -> height }
    val exitAnimation = slideOutVertically(
        animationSpec = tween(durationMillis = SHEET_ANIMATION_DURATION, easing = sheetEasing)
    ) { height -> height }

    internal fun lerpDist(maxPx: Float, progress: Float): Float {
        if (maxPx.isNaN() || maxPx == 0f) return 0f
        return maxPx * progress.coerceIn(0f, 1f)
    }

    // Extension to apply predictive translation on Modifier
    fun Modifier.containerPredictiveAnim(backProgress: Float): Modifier = this.then(
        Modifier.graphicsLayer {
            val distance = predictiveBackMaxDistanceContainer.toPx()
            translationY = lerpDist(distance, backProgress)
        }
    )

    @Composable
    fun calculateShape(shape: RoundedCornerShape): Dp {
        val density = LocalDensity.current
        val cornerSize = shape.topStart
        val cornerSizePx = cornerSize.toPx(Size.Companion.Unspecified, density)
        return with(density) { cornerSizePx.toDp() }
    }

    /**
     * Считает анимированную форму в зависимости от backProgress.
     * Возвращает RoundedCornerShape с анимированными верхними углами.
     */
    @Composable
    fun animatedShape(backProgress: Float, shape: RoundedCornerShape): RoundedCornerShape {
        val cornerSize = calculateShape(shape)
        val height = if (backProgress > 0f) backProgress else 0f
        val fraction = height * SHAPE_FRACTION_MODIFIER
        val animatedCornerSize = lerp(cornerSize, minCornerSize, fraction.coerceIn(0f, 1f))

        return RoundedCornerShape(
            topStart = animatedCornerSize,
            topEnd = animatedCornerSize,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        )
    }
}
