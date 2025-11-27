package ru.dailycode.pack.decompose_ext.animations.platform

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackAnimatable
import com.arkivanov.essenty.backhandler.BackEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ru.thedevs.libraries.decompose.compose.animations.utils.IosAnimationConsts.iosEasing

@OptIn(ExperimentalDecomposeApi::class)
internal class IosPredictiveBackAnimatable(
    initialEvent: BackEvent,
) : PredictiveBackAnimatable {

    private val exitProgressAnimatable = Animatable(initialEvent.progress.exit())
    private val exitProgress by derivedStateOf { exitProgressAnimatable.value }

    private val enterProgressAnimatable = Animatable(initialEvent.progress.enter())
    private val enterProgress by derivedStateOf { enterProgressAnimatable.value }

    private val finishAnim = Animatable(0f)
    private val finishProgress by derivedStateOf { finishAnim.value }

    private var edge by mutableStateOf(initialEvent.swipeEdge)


    override val exitModifier: Modifier
        get() = Modifier
            .zIndex(1f)
            .graphicsLayer {
                val p = exitProgress
                translationX = size.width * (p + (1f - p) * finishProgress)
                alpha = 1f
            }


    override val enterModifier: Modifier
        get() = Modifier
            .zIndex(0f)
            .graphicsLayer {
                val p = enterProgress + finishProgress * (1f - enterProgress)
                translationX = size.width * (p - 1f)
                alpha = 1f
            }
            .drawWithContent {
                drawContent()
                val overlay = (1f - enterProgress) * IosAnimationConsts.MAX_OVERLAY_ALPHA
                drawRect(Color.Black.copy(alpha = overlay))
            }


    override suspend fun animate(event: BackEvent) {
        edge = event.swipeEdge
        exitProgressAnimatable.snapTo(event.progress.exit())
        enterProgressAnimatable.snapTo(event.progress.enter())
    }


    override suspend fun finish() {
        awaitAll(
            {
                exitProgressAnimatable.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = IosAnimationConsts.FINISH_DURATION,
                        easing = iosEasing
                    )
                )
            },
            {
                finishAnim.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = IosAnimationConsts.FINISH_DURATION,
                        easing = iosEasing
                    )
                )
            }
        )
    }


    override suspend fun cancel() {
        awaitAll(
            {
                exitProgressAnimatable.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = IosAnimationConsts.CANCEL_DURATION,
                        easing = iosEasing
                    )
                )
            },
            {
                enterProgressAnimatable.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = IosAnimationConsts.CANCEL_DURATION,
                        easing = iosEasing
                    )
                )
            }
        )
    }


    private suspend fun awaitAll(vararg actions: suspend CoroutineScope.() -> Unit) {
        coroutineScope { actions.map { launch(block = it) }.joinAll() }
    }
}

private fun Float.exit(): Float =
    this.coerceIn(0f, 1f)


private fun Float.enter(): Float =
    if (this < IosAnimationConsts.PROGRESS_THRESHOLD) {
        IosAnimationConsts.MIN_ENTER_PROGRESS * (this / IosAnimationConsts.PROGRESS_THRESHOLD)
    } else {
        lerp(
            start = IosAnimationConsts.MIN_ENTER_PROGRESS,
            stop = 1f,
            fraction = (this - IosAnimationConsts.PROGRESS_THRESHOLD) /
                    (1f - IosAnimationConsts.PROGRESS_THRESHOLD)
        )
    }