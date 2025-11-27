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
import androidx.compose.ui.layout.layout
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimationScope
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimator
import com.arkivanov.decompose.extensions.compose.stack.animation.Direction
import com.arkivanov.decompose.extensions.compose.stack.animation.isFront

@OptIn(ExperimentalDecomposeApi::class)
public fun slideAnimator(
    isVertical: Boolean,
    isBackward: Boolean,
): StackAnimator {
    return SlideSharedStackAnimator(isBackward = isBackward) { factor, _ ->
        if (isVertical) {
            Modifier.offsetYFactor(factor)
        } else {
            Modifier.offsetXFactor(factor)
        }
    }
}
private fun Modifier.offsetXFactor(
    factor: Float,
): Modifier = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    layout(placeable.width, placeable.height) {
        placeable.placeRelative(x = ((placeable.width.toFloat() * 0.075f) * factor).toInt(), y = 0)
    }
}

private fun Modifier.offsetYFactor(
    factor: Float,
): Modifier = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    layout(placeable.width, placeable.height) {
        placeable.placeRelative(y = ((placeable.width.toFloat() * 0.075f) * factor).toInt(), x = 0)
    }
}

@OptIn(ExperimentalDecomposeApi::class)
internal class SlideSharedStackAnimator(
    private val isBackward: Boolean,
    private val frame: @Composable (factor: Float, direction: Direction) -> Modifier,
) : StackAnimator {
    @Composable
    override fun StackAnimationScope.animate(direction: Direction): Modifier {
        val animationSpec = getSlideAnimationSpec(direction)
        val factor by transition.animateFloat(transitionSpec = { animationSpec }) { state ->
            when (state) {
                EnterExitState.Visible -> 0f
                EnterExitState.PreEnter,
                EnterExitState.PostExit -> if (direction.isFront) 1f else -1f
            }
        }

        val finalFactor = if (isBackward) -factor else factor
        return frame(finalFactor, direction)
    }
}

private fun getSlideAnimationSpec(direction: Direction): FiniteAnimationSpec<Float> {
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
