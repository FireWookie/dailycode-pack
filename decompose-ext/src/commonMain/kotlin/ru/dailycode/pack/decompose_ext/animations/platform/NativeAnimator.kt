package ru.dailycode.pack.decompose_ext.animations.platform

import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackAnimatable
import com.arkivanov.essenty.backhandler.BackHandler

@OptIn(ExperimentalDecomposeApi::class)
expect fun <C : Any, T : Any> nativeAnimator(
    backHandler: BackHandler,
    isBackward: Boolean,
    onBack: () -> Unit,
    animatable: PredictiveBackAnimatable? = null
): StackAnimation<C, T>
