package ru.dailycode.pack.decompose_ext.animations

import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.PredictiveBackParams
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackAnimatable
import com.arkivanov.essenty.backhandler.BackHandler
import ru.dailycode.pack.decompose_ext.animations.animations.fadeAnimator
import ru.dailycode.pack.decompose_ext.animations.animations.slideAnimator

@OptIn(ExperimentalDecomposeApi::class)
internal fun <C : Any, T : Any> defaultAnimation(
    backHandler: BackHandler,
    isBackward: Boolean,
    onBack: () -> Unit,
    animatable: PredictiveBackAnimatable? = null
) = stackAnimation<C, T>(
    animator = slideAnimator(false, isBackward) + fadeAnimator(),
    predictiveBackParams = {
        PredictiveBackParams(
            backHandler = backHandler,
            onBack = onBack,
            animatable = { animatable },
        )
    },
)
