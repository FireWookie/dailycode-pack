package ru.dailycode.pack.decompose_ext.animations.platform

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimator
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimator
import com.arkivanov.decompose.extensions.compose.stack.animation.isFront

@OptIn(ExperimentalDecomposeApi::class)
internal fun iosSlide(
    animationSpec: FiniteAnimationSpec<Float> = tween(
        durationMillis = IosAnimationConsts.DEFAULT_DURATION,
        easing = IosAnimationConsts.iosEasing
    ),
): StackAnimator = stackAnimator(animationSpec = animationSpec) { factor, direction ->
    Modifier
        .then(if (direction.isFront) Modifier else Modifier.fadeIos(factor))
        .offsetXFactor(
            factor = if (direction.isFront) {
                factor
            } else {
                (factor * IosAnimationConsts.BACK_OFFSET_FACTOR)
                    .coerceIn(-1f, 1f)
            }
        )
}
