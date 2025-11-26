package ru.dailycode.pack.compose_ext.components.input.bottom_sheet

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.internal.BottomSheetContentContainer
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.internal.BottomSheetDragHandleContainer
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.internal.BottomSheetScaffoldLayout
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.internal.BottomSheetScrim
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils.BackHandlerProvider
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils.BottomSheetBackHandle
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils.DailyBottomSheetConfig
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils.DailyBottomSheetState
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils.DailyBottomSheetStateValue
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils.LocalBackHandlerProvider
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils.LocalDailyBottomSheetConfig
import ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils.rememberDailyBottomSheetState
import ru.dailycode.pack.compose_ext.utils.DeviceInsets

@Composable
public fun DailyModalBottomSheet(
    dismiss: () -> Unit,
    modifier: Modifier = Modifier,
    backHandlerProvider: BackHandlerProvider? = LocalBackHandlerProvider.current,
    config: DailyBottomSheetConfig = LocalDailyBottomSheetConfig.current,
    sheetState: DailyBottomSheetState = rememberDailyBottomSheetState(),
    canHide: Boolean = true,
    content: @Composable ColumnScope.(hideSheet: () -> Unit, PaddingValues) -> Unit,
) {
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()

    val offsetAnim = remember { Animatable(0f) }

    LaunchedEffect(sheetState.offsetTarget) {
        offsetAnim.animateTo(targetValue = sheetState.offsetTarget, animationSpec = tween(durationMillis = 300))
    }

    val handlerData = remember(sheetState, canHide) {
        BottomSheetBackHandle(
            canHide = canHide,
            onBack = { sheetState.requestHide() },
            callbackProgress = { progress ->
                sheetState.updateBackButtonProgress(progress)
                sheetState.updateUnifiedProgress()
            }
        )
    }

    backHandlerProvider?.Register(handlerData)

    val statusBarPadding = if (sheetState.isFillMaxSize) DeviceInsets.statusBarPadding() else 0.dp

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(config.enableSwipeToDismiss, canHide, sheetState.contentHeightPx) {
                if (config.enableSwipeToDismiss && canHide) {
                    detectVerticalDragGestures(
                        onDragStart = { sheetState.updateDragging(true) },
                        onDragEnd = {
                            sheetState.updateDragging(false)
                            scope.launch {
                                val currentOffset = offsetAnim.value
                                val threshold = sheetState.contentHeightPx * config.swipeToDismissThreshold

                                if (currentOffset > threshold) {
                                    sheetState.requestHide()
                                    offsetAnim.animateTo(
                                        targetValue = sheetState.contentHeightPx,
                                        animationSpec = tween(300)
                                    )
                                } else {
                                    offsetAnim.animateTo(0f, tween(300))
                                }
                            }
                        },
                        onDragCancel = {
                            sheetState.updateDragging(false)
                            scope.launch { offsetAnim.animateTo(targetValue = 0f, animationSpec = tween(300)) }
                        },
                        onVerticalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset = (offsetAnim.value + dragAmount).coerceAtLeast(0f)
                                offsetAnim.snapTo(newOffset)
                                sheetState.updateTargetOffset(newOffset)
                                sheetState.updateUnifiedProgress()
                            }
                        }
                    )
                }
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        val fullHeightPx = with(density) { this@BoxWithConstraints.maxHeight.toPx() }

        BottomSheetScrim(
            isVisible = sheetState.isVisible,
            enabled = canHide,
            backProgress = sheetState.unifiedProgress
        ) {
            sheetState.requestHide()
        }

        val targetShape = remember(sheetState.value, config.shapes) {
            if (sheetState.isFillMaxSize) {
                config.shapes ?: RoundedCornerShape(0.dp)
            } else {
                RoundedCornerShape(topEnd = 38.dp, topStart = 38.dp)
            }
        }

        BottomSheetContentContainer(
            visible = sheetState.isVisible,
            backProgress = sheetState.unifiedProgress,
            modifier = modifier.heightIn(min = config.minimumHeight, max = maxHeight),
            containerColor = config.containerColor,
            shape = targetShape,
            isFillMaxSize = sheetState.isFillMaxSize,
            onCoordinatesSet = { coords ->
                val h = coords.size.height.toFloat()
                if (h > 0f && sheetState.contentHeightPx != h) {
                    sheetState.updateContentHeightPx(h.coerceAtMost(fullHeightPx))
                }
            }
        ) {
            BottomSheetScaffoldLayout(
                topBar = {
                    BottomSheetDragHandleContainer(
                        color = config.containerColor.copy(alpha = 0.9f),
                        paddings = PaddingValues(top = statusBarPadding),
                        dragHandle = { config.dragHandle?.let { it { sheetState.requestHide() } } }
                    )
                },
                fabPosition = FabPosition.End,
                customPaddingValues = PaddingValues(),
                content = { content.invoke(this, { sheetState.requestHide() }, it) }
            )
        }
    }

    LaunchedEffect(Unit) { sheetState.showPartially() }

    LaunchedEffect(sheetState.value) {
        if (sheetState.value == DailyBottomSheetStateValue.HIDDEN) {
            offsetAnim.animateTo(sheetState.contentHeightPx, tween(300))
            dismiss()
        }
    }
}
