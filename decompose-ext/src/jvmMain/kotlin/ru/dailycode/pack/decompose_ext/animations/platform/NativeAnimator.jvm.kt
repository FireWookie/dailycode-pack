package ru.dailycode.pack.decompose_ext.animations.platform

import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackAnimatable
import com.arkivanov.essenty.backhandler.BackHandler
import ru.dailycode.pack.decompose_ext.animations.defaultAnimation

@OptIn(markerClass = [ExperimentalDecomposeApi::class])
actual fun <C : Any, T : Any> nativeAnimator(
    backHandler: BackHandler,
    isBackward: Boolean,
    onBack: () -> Unit,
    animatable: PredictiveBackAnimatable?
): StackAnimation<C, T> =
    defaultAnimation(
        backHandler = backHandler,
        isBackward = isBackward,
        onBack = onBack,
        animatable = animatable
    )
