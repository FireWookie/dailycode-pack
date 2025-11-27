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
import androidx.compose.ui.draw.scale
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimationScope
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimator
import com.arkivanov.decompose.extensions.compose.stack.animation.Direction
import com.arkivanov.decompose.extensions.compose.stack.animation.isFront

@OptIn(ExperimentalDecomposeApi::class)
fun scaleAnimator(
    frontFactor: Float = 0.98f,
    backFactor: Float = 0.98F,
): StackAnimator =
    ScaleSharedStackAnimator { factor, _ ->
        Modifier.scale(
            if (factor >= 0F) {
                factor * (frontFactor - 1F) + 1F
            } else {
                factor * (1F - backFactor) + 1F
            }
        )
    }

@OptIn(ExperimentalDecomposeApi::class)
internal class ScaleSharedStackAnimator(
    private val frame: @Composable (factor: Float, direction: Direction) -> Modifier
) : StackAnimator {
    @Composable
    override fun StackAnimationScope.animate(direction: Direction): Modifier {
        val animationSpec = getScaleAnimationSpec(direction)
        val factor by transition.animateFloat(transitionSpec = { animationSpec }) { state ->
            when (state) {
                EnterExitState.Visible -> 0F
                EnterExitState.PreEnter -> if (direction.isFront) -1F else -1F
                EnterExitState.PostExit -> if (direction.isFront) -1F else -1F
            }
        }
        return frame(factor, direction)
    }
}

private fun getScaleAnimationSpec(direction: Direction): FiniteAnimationSpec<Float> {
    return when (direction) {
        Direction.ENTER_FRONT, Direction.ENTER_BACK -> tween(
            durationMillis = ENTER_DURATION,
            easing = FastOutSlowInEasing
        )
        Direction.EXIT_FRONT, Direction.EXIT_BACK -> tween(
            durationMillis = ENTER_DURATION,
            easing = LinearOutSlowInEasing
        )
    }
}
