package ru.dailycode.pack.decompose_ext.compose

import kotlinx.coroutines.flow.StateFlow

/**
 * Общий интерфейс для компонентов модальных Bottom Sheet.
 */
public interface BottomSheetContentComponent <S: Any> {

    public val bottomSheetContentState: StateFlow<S>
}