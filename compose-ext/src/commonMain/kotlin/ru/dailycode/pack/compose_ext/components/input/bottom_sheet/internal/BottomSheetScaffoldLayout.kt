package ru.dailycode.pack.compose_ext.components.input.bottom_sheet.internal

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMapNotNull
import androidx.compose.ui.util.fastMaxBy

@Composable
internal fun BottomSheetScaffoldLayout(
    topBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    fab: @Composable () -> Unit = {},
    fabPosition: FabPosition,
    snackbar: @Composable () -> Unit = {},
    customPaddingValues: PaddingValues,
    contentWindowInsets: WindowInsets = WindowInsets(0.dp),
) {
    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val topBarPlaceables =
            subcompose(ScaffoldLayoutContent.TopBar, topBar).fastMap {
                it.measure(looseConstraints)
            }
        val topBarHeight = topBarPlaceables.fastMaxBy { it.height }?.height ?: 0

        val snackbarPlaceables =
            subcompose(ScaffoldLayoutContent.Snackbar, snackbar).fastMap {
                val leftInset =
                    contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection)
                val rightInset =
                    contentWindowInsets.getRight(this@SubcomposeLayout, layoutDirection)
                val bottomInset = contentWindowInsets.getBottom(this@SubcomposeLayout)
                it.measure(looseConstraints.offset(-leftInset - rightInset, -bottomInset))
            }
        val snackbarHeight = snackbarPlaceables.fastMaxBy { it.height }?.height ?: 0
        val snackbarWidth = snackbarPlaceables.fastMaxBy { it.width }?.width ?: 0

        val bodyContentPlaceables =
            subcompose(ScaffoldLayoutContent.MainContent) {
                val insets = contentWindowInsets.asPaddingValues(this@SubcomposeLayout)
                val innerPadding =
                    PaddingValues(
                        top = topBarHeight.toDp() + customPaddingValues.calculateTopPadding(),
                        bottom = customPaddingValues.calculateBottomPadding(),
                        start = insets.calculateStartPadding(layoutDirection) + customPaddingValues.calculateLeftPadding(
                            layoutDirection
                        ),
                        end = insets.calculateEndPadding(layoutDirection) + customPaddingValues.calculateRightPadding(
                            layoutDirection
                        )
                    )
                content(innerPadding)
            }
                .fastMap { it.measure(looseConstraints) }

        val contentHeight =
            bodyContentPlaceables.fastMaxBy { it.height }?.height ?: layoutHeight
        val layoutFinalHeight = minOf(contentHeight, layoutHeight)

        val fabPlaceables =
            subcompose(ScaffoldLayoutContent.FAB, fab).fastMapNotNull { measurable ->
                // respect only bottom and horizontal for snackbar and fab
                val leftInset = contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection)
                val rightInset =
                    contentWindowInsets.getRight(this@SubcomposeLayout, layoutDirection)
                val bottomInset = contentWindowInsets.getBottom(this@SubcomposeLayout)
                measurable
                    .measure(looseConstraints.offset(-leftInset - rightInset, -bottomInset))
                    .takeIf { it.height != 0 && it.width != 0 }
            }

        val fabPlacement =
            if (fabPlaceables.isNotEmpty()) {
                val fabWidth = fabPlaceables.fastMaxBy { it.width }!!.width
                val fabHeight = fabPlaceables.fastMaxBy { it.height }!!.height
                // FAB distance from the left of the layout, taking into account LTR / RTL
                val fabLeftOffset =
                    when (fabPosition) {
                        FabPosition.Start -> {
                            if (layoutDirection == LayoutDirection.Ltr) {
                                FabSpacing.roundToPx()
                            } else {
                                layoutWidth - FabSpacing.roundToPx() - fabWidth
                            }
                        }
                        FabPosition.End,
                        FabPosition.EndOverlay -> {
                            if (layoutDirection == LayoutDirection.Ltr) {
                                layoutWidth - FabSpacing.roundToPx() - fabWidth
                            } else {
                                FabSpacing.roundToPx()
                            }
                        }
                        else -> (layoutWidth - fabWidth) / 2
                    }

                FabPlacement(left = fabLeftOffset, width = fabWidth, height = fabHeight)
            } else {
                null
            }
        layout(layoutWidth, layoutFinalHeight) {
            bodyContentPlaceables.fastForEach { it.place(0, 0) }
            topBarPlaceables.fastForEach { it.place(0, 0) }

            val bottomInset = contentWindowInsets.getBottom(this@SubcomposeLayout)

            fabPlacement?.let { placement ->
                fabPlaceables.fastForEach {
                    it.place(
                        placement.left,
                        layoutHeight -
                            placement.height -
                            FabSpacing.roundToPx() -
                            bottomInset -
                            customPaddingValues.calculateBottomPadding().roundToPx()
                    )
                }
            }

            snackbarPlaceables.fastForEach {
                it.place(
                    (layoutWidth - snackbarWidth) / 2 +
                        contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection),
                    layoutHeight - snackbarHeight - bottomInset
                )
            }
        }
    }
}
private enum class ScaffoldLayoutContent {
    TopBar,
    MainContent,
    Snackbar,
    BottomBar,
    FAB
}

@Immutable
internal class FabPlacement(val left: Int, val width: Int, val height: Int)
private val FabSpacing = 16.dp
