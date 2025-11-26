package ru.dailycode.pack.compose_ext.components.input.bottom_sheet.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun BottomSheetDragHandleContainer(
    color: Color,
    paddings: PaddingValues = PaddingValues(),
    modifier: Modifier = Modifier,
    dragHandle: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color)
            .padding(paddings),
        contentAlignment = Alignment.Center
    ) {
        dragHandle()
    }
}
