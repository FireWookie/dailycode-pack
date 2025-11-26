package ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlin.math.max

@Stable
public class DailyBottomSheetState internal constructor(
    initialValue: DailyBottomSheetStateValue,
    internal val skipPartiallyExpanded: Boolean,
    internal val expandToFillMaxSize: Boolean,
    internal val confirmValueChange: (DailyBottomSheetStateValue) -> Boolean,
    internal val skipHiddenState: Boolean,
) {
    public var value: DailyBottomSheetStateValue by mutableStateOf(initialValue)
        private set

    private var _unifiedProgress by mutableStateOf(0f)
    public val unifiedProgress: Float get() = _unifiedProgress

    public fun updateUnifiedProgress() {
        val maxPx = (contentHeightPx).let { if (it <= 0f) 1f else it }
        val drag = (offsetTarget / maxPx).coerceIn(0f, 1f)
        _unifiedProgress = max(backButtonProgress, drag)
    }

    public val isVisible: Boolean get() = value != DailyBottomSheetStateValue.HIDDEN
    public val isFillMaxSize: Boolean get() = value == DailyBottomSheetStateValue.FILL_MAX_SIZE

    public var backButtonProgress: Float by mutableStateOf(0f)
        internal set

    public var backButtonProgressHeight: Float by mutableStateOf(0f)
        internal set

    public var isDragging: Boolean by mutableStateOf(false)
        internal set

    public var contentHeightPx: Float by mutableStateOf(0f)
        internal set

    public var offsetTarget: Float by mutableStateOf(0f)
        internal set

    private fun transitionTo(newValue: DailyBottomSheetStateValue) {
        if (confirmValueChange(newValue)) value = newValue
    }

    public fun showPartially(): Unit = transitionTo(DailyBottomSheetStateValue.CONTENT_HEIGHT)
    public fun showFillMax(): Unit = transitionTo(DailyBottomSheetStateValue.FILL_MAX_SIZE)
    public fun hide(): Unit = transitionTo(DailyBottomSheetStateValue.HIDDEN)
    public fun requestHide(): Unit = hide()

    internal fun updateBackButtonProgress(progress: Float) {
        backButtonProgress = progress
    }

    internal fun updateDragging(dragging: Boolean) {
        isDragging = dragging
    }

    internal fun updateContentHeightPx(h: Float) {
        contentHeightPx = h
    }

    internal fun updateTargetOffset(target: Float) {
        offsetTarget = target
    }
}

@Composable
public fun rememberDailyBottomSheetState(
    skipPartiallyExpanded: Boolean = false,
    expandToFillMaxSize: Boolean = false,
    initialValue: DailyBottomSheetStateValue = DailyBottomSheetStateValue.HIDDEN,
    confirmValueChange: (DailyBottomSheetStateValue) -> Boolean = { true },
    skipHiddenState: Boolean = false,
): DailyBottomSheetState {
    return remember {
        DailyBottomSheetState(
            initialValue = initialValue,
            skipPartiallyExpanded = skipPartiallyExpanded,
            expandToFillMaxSize = expandToFillMaxSize,
            confirmValueChange = confirmValueChange,
            skipHiddenState = skipHiddenState
        )
    }
}
