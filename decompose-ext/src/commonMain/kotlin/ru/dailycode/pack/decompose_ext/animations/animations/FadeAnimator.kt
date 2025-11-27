@file:OptIn(ExperimentalDecomposeApi::class)

package ru.dailycode.pack.decompose_ext.animations.animations

import androidx.compose.animation.EnterExitState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimationScope
// import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimationScope
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimator
import com.arkivanov.decompose.extensions.compose.stack.animation.Direction
import com.arkivanov.decompose.extensions.compose.stack.animation.isFront
import kotlin.math.abs

public fun fadeAnimator(): StackAnimator {
    return FadeSharedStackAnimator { factor, _ ->
        Modifier.alpha(getFadeAlpha(factor = factor, minAlpha = 0f))
    }
}

private class FadeSharedStackAnimator(
    private val frame: @Composable (factor: Float, direction: Direction) -> Modifier
) : StackAnimator {
    @Composable
    override fun StackAnimationScope.animate(direction: Direction): Modifier {
        val animationSpec = getFadeAnimationSpec(direction)
        val factor by transition.animateFloat(transitionSpec = { animationSpec }) { state ->
            when (state) {
                EnterExitState.Visible -> 0F
                EnterExitState.PreEnter,
                EnterExitState.PostExit -> if (direction.isFront) 1F else -1F
            }
        }
        return frame(factor, direction)
    }
}

private fun getFadeAlpha(factor: Float, minAlpha: Float): Float =
    (1F - abs(factor) * (1F - minAlpha)).coerceIn(minimumValue = 0F, maximumValue = 1F)

private fun getFadeAnimationSpec(direction: Direction): FiniteAnimationSpec<Float> {
    return when (direction) {
        Direction.ENTER_FRONT -> tween(
            durationMillis = FADE_IN_DURATION,
            delayMillis = FADE_ENTER_DELAY,
            easing = FastOutSlowInEasing
        )
        Direction.ENTER_BACK -> tween(
            durationMillis = FADE_IN_DURATION,
            delayMillis = FADE_ENTER_DELAY,
            easing = FastOutSlowInEasing
        )
        Direction.EXIT_FRONT -> tween(
            durationMillis = FADE_OUT_DURATION,
            easing = LinearOutSlowInEasing
        )
        Direction.EXIT_BACK -> tween(
            durationMillis = FADE_OUT_DURATION,
            easing = LinearOutSlowInEasing
        )
    }
}
