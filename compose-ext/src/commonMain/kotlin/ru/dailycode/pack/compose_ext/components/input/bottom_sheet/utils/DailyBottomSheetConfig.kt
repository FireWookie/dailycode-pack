package ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

public data class DailyBottomSheetConfig(
    val dragHandle: (@Composable (hideSheet: () -> Unit) -> Unit)?,
    val shapes: RoundedCornerShape?,
    val minimumHeight: Dp,
    val containerColor: Color,
    val swipeToDismissThreshold: Float,
    val enableSwipeToDismiss: Boolean,
)


public object DailyBottomSheetDefaults {
    internal const val SWIPE_TO_DISMISS = 0.3f

    public val defaultConfig: DailyBottomSheetConfig = DailyBottomSheetConfig(
        dragHandle = null,
        shapes = null,
        minimumHeight = 150.dp,
        containerColor = Color.LightGray,
        swipeToDismissThreshold = SWIPE_TO_DISMISS,
        enableSwipeToDismiss = true
    )
}

public val LocalDailyBottomSheetConfig: CompositionLocal<DailyBottomSheetConfig> = staticCompositionLocalOf { DailyBottomSheetDefaults.defaultConfig }