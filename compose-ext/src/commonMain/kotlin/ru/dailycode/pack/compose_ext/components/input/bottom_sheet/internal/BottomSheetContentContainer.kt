package ru.dailycode.pack.compose_ext.components.input.bottom_sheet.internal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.internal.DailyBottomSheetConstants.animatedShape
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.internal.DailyBottomSheetConstants.containerPredictiveAnim
import ru.dailycode.pack.compose_ext.modifiers.thenIf
import ru.dailycode.pack.compose_ext.utils.DeviceInsets

@Composable
internal fun BottomSheetContentContainer(
    visible: Boolean,
    backProgress: Float,
    containerColor: Color,
    shape: RoundedCornerShape,
    isFillMaxSize: Boolean,
    onCoordinatesSet: (LayoutCoordinates) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val topPadding = DeviceInsets.statusBarPadding() + 10.dp
    AnimatedVisibility(
        visible = visible,
        enter = DailyBottomSheetConstants.enterAnimation,
        exit = DailyBottomSheetConstants.exitAnimation,
        modifier = Modifier.containerPredictiveAnim(backProgress)
    ) {
        Column(
            modifier = modifier
                .pointerInput(Unit) { detectTapGestures {} }
                .thenIf(!isFillMaxSize) { padding(top = topPadding) }
                .clip(animatedShape(backProgress = backProgress, shape = shape))
                .background(containerColor)
                .fillMaxWidth()
                .onGloballyPositioned(onCoordinatesSet),
            content = content
        )
    }
}
