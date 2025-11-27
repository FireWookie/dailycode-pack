package ru.dailycode.pack.compose_ext.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

object DeviceInsets {
    private val density: Density
        @Composable
        @ReadOnlyComposable
        get() = LocalDensity.current

    /**
     * Возвращает высоту статус-бара в dp.
     * Полезно для позиционирования элементов под системной верхней панелью.
     */
    @Composable
    @NonRestartableComposable
    fun statusBarPadding(): Dp {
        val topPaddingPx = WindowInsets.Companion.statusBars.getTop(density)
        return with(density) { topPaddingPx.toDp() }
    }

    /**
     * Возвращает высоту нижней системной панели (navigation bar) в dp.
     */
    @Composable
    @NonRestartableComposable
    public fun navigationBarsPadding(): Dp {
        val bottomPaddingPx = WindowInsets.Companion.navigationBars.getBottom(density)
        return with(density) { bottomPaddingPx.toDp() }
    }

    /**
     * Возвращает высоту области под клавиатуру (IME) в dp.
     * Если клавиатура не открыта, значение будет 0.dp.
     */
    @Composable
    fun imePadding(): Dp {
        val imePaddingPx = WindowInsets.Companion.ime.getBottom(density)
        return with(density) { imePaddingPx.toDp() }
    }

    /**
     * Универсальный вариант для нижнего отступа.
     *
     * Если клавиатура открыта → берётся её высота (imePadding).
     * Если клавиатура закрыта → используется высота нижней системной панели (navigationBarsPadding).
     *
     * Таким образом, отступ снизу всегда корректен,
     * и в отличие от одновременного применения imePadding + navigationBarsPadding,
     * здесь значения **не суммируются**.
     *
     * Рекомендуется использовать именно этот метод для большинства кейсов
     * (например, контейнеры экранов или scrollable-контент),
     * чтобы избежать двойного отступа снизу.
     */
    @Composable
    fun navigationOrImePadding(): Dp {
        val navigationPaddingPx = WindowInsets.Companion.navigationBars.getBottom(density)
        val imePaddingPx = WindowInsets.Companion.ime.getBottom(density)
        val bottomPaddingPx = if (imePaddingPx > navigationPaddingPx) imePaddingPx else navigationPaddingPx
        return with(density) { bottomPaddingPx.toDp() }
    }
}
