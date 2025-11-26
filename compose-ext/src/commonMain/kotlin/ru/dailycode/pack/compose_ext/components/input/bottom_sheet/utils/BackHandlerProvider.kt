package ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
public fun interface BackHandlerProvider {
    @Composable
    public fun Register(handle: BottomSheetBackHandle)
}
