package ru.dailycode.pack.decompose_ext.animations.platform

import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackAnimatable
import com.arkivanov.essenty.backhandler.BackEvent

@OptIn(ExperimentalDecomposeApi::class)
fun iosPredictiveBackAnimatable(
    initialBackEvent: BackEvent,
): PredictiveBackAnimatable =
    IosPredictiveBackAnimatable(initialEvent = initialBackEvent)
