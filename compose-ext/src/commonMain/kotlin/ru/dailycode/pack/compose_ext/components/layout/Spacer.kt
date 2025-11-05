package ru.dailycode.pack.compose_ext.components.layout

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
@NonRestartableComposable
fun ColumnScope.DailySpacer(height: Dp) = Spacer(Modifier.height(height))

@Composable
@NonRestartableComposable
fun RowScope.DailySpacer(width: Dp) = Spacer(Modifier.width(width))

@Composable
fun ColumnScope.DailySpacerWeight(
    weight: Float = 1f,
    maxHeight: Dp? = null
) {
    // TODO(FIX MAX HEIGHT)
    val maxHeightPx = maxHeight?.let { with(LocalDensity.current) { it.toPx() } }

    Layout(
        content = {},
        modifier = Modifier
            .weight(weight, fill = true)
    ) { _, constraints ->
        val targetHeight = if (maxHeightPx != null) {
            minOf(constraints.maxHeight.toFloat(), maxHeightPx).toInt()
        } else {
            constraints.maxHeight
        }

        layout(constraints.maxWidth, targetHeight) {}
    }
}

@Composable
@NonRestartableComposable
fun RowScope.DailySpacerWeight(weight: Float = 1f) = Spacer(Modifier.weight(weight))

fun LazyListScope.lazySpacer(
    height: Dp,
    width: Dp = height,
    modifier: Modifier = Modifier
) = item(contentType = "spacer_${height.value}_${width.value}") {
    Spacer(modifier.size(width = width, height = height))
}