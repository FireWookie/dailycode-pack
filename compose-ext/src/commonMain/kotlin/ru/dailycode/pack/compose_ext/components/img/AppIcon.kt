package ru.dailycode.pack.compose_ext.components.img

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import ru.dailycode.pack.compose_ext.utils.StringDesc
import ru.dailycode.pack.compose_ext.utils.asString

@Composable
fun AppIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    description: StringDesc? = null,
    tint: Color = Color.Unspecified,
    size: Dp = Dp.Unspecified,
    containerSize: Dp = size,
) {
    Box(
        modifier = modifier
            .size(containerSize),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = description?.asString() ?: imageVector.name,
            tint = tint,
            modifier = Modifier
                .requiredSize(size)
        )
    }
}
