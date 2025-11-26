package ru.dailycode.pack.compose_ext.components.input.bottom_sheet.utils

import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

public val LocalBackHandlerProvider: CompositionLocal<BackHandlerProvider?> = staticCompositionLocalOf<BackHandlerProvider?> {
    null
}
