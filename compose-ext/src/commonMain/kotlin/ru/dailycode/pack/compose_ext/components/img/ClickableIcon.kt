package ru.dailycode.pack.compose_ext.components.img

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.dailycode.pack.compose_ext.utils.StringDesc

data class ClickableIconConfig(
    val disabledImageVector: ImageVector?,
    val iconSize: Dp,
    val containerSize: Dp,
    val shape: Shape,
    val containerColor: Color,
)

object ClickableIconDefaults {
    val params: ClickableIconConfig = ClickableIconConfig(
        disabledImageVector = null,
        containerSize = 30.dp,
        iconSize = Dp.Unspecified,
        shape = CircleShape,
        containerColor = Color.Unspecified
    )
}

@Composable
fun ClickableIcon(
    imageVector: ImageVector,
    config: ClickableIconConfig,
    description: StringDesc? = null,
    enabled: Boolean = true,
    tint: Color = Color.Unspecified,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(config.containerSize)
            .background(config.containerColor, config.shape)
            .clip(config.shape)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        AppIcon(
            imageVector = if (enabled) imageVector else config.disabledImageVector ?: imageVector,
            description = description,
            tint = tint,
            size = if (config.iconSize == Dp.Unspecified) (config.containerSize / 2) else config.iconSize,
        )
    }
}
