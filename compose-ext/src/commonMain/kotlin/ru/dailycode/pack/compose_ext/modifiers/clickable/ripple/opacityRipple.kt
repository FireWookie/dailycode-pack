package ru.dailycode.pack.compose_ext.modifiers.clickable.ripple

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.unit.Constraints
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

@Stable
fun opacityRipple(
    fadeInDuration: Int,
    fadeOutDuration: Int,
    minAlpha: Float = 0.4f
): IndicationNodeFactory =
    OpacityRippleFactory(fadeInDuration, fadeOutDuration, minAlpha)

@Stable
internal class OpacityRippleFactory(
    private val fadeInSpec: FiniteAnimationSpec<Float>,
    private val fadeOutSpec: FiniteAnimationSpec<Float>,
    private val minAlpha: Float,
) : IndicationNodeFactory {
    constructor(
        fadeInDuration: Int,
        fadeOutDuration: Int,
        minAlpha: Float,
    ) : this(
        tween(fadeInDuration),
        tween(fadeOutDuration),
        minAlpha
    )

    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return OpacityRippleNode(
            fadeInSpec = fadeInSpec,
            fadeOutSpec = fadeOutSpec,
            minAlpha = minAlpha,
            interactionSource = interactionSource
        )
    }

    override fun hashCode(): Int {
        var result = fadeInSpec.hashCode()
        result = 31 * result + fadeOutSpec.hashCode()
        result = 31 * result + minAlpha.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OpacityRippleFactory) return false
        if (fadeInSpec != other.fadeInSpec) return false
        if (minAlpha != other.minAlpha) return false
        return fadeOutSpec == other.fadeOutSpec
    }
}

class OpacityRippleNode(
    private val interactionSource: InteractionSource,
    private val fadeInSpec: FiniteAnimationSpec<Float>,
    private val fadeOutSpec: FiniteAnimationSpec<Float>,
    private val minAlpha: Float
) : Modifier.Node(),
    DelegatableNode,
    LayoutModifierNode {
    val animatedAlpha = Animatable(1f)
    override val shouldAutoInvalidate: Boolean = false

    private val layerBlock: GraphicsLayerScope.() -> Unit = {
        alpha = animatedAlpha.value
    }

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            placeable.placeWithLayer(0, 0, layerBlock = layerBlock)
        }
    }

    private var pressJob: Job? = null

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions
                .filterIsInstance<PressInteraction>()
                .collect { interaction ->
                    handlePressInteraction(interaction)
                }
        }
    }

    private fun handlePressInteraction(
        pressInteraction: PressInteraction
    ) {
        pressJob?.cancel()
        pressJob = coroutineScope.launch {
            when (pressInteraction) {
                is PressInteraction.Press -> fadeOut()

                is PressInteraction.Release,
                is PressInteraction.Cancel -> fadeIn()
            }
        }
    }

    private suspend fun fadeOut() {
        animatedAlpha.animateTo(
            targetValue = minAlpha,
            animationSpec = fadeOutSpec
        )
    }

    private suspend fun fadeIn() {
        animatedAlpha.animateTo(
            targetValue = 1f,
            animationSpec = fadeInSpec
        )
    }
}
